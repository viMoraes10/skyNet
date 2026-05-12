package com.skyNet.service.impl;

import com.skyNet.Enum.dashboard.DashboardGroupBy;
import com.skyNet.dto.dashboard.*;
import com.skyNet.model.Camera;
import com.skyNet.model.Occurrence;
import com.skyNet.repository.CameraRepository;
import com.skyNet.repository.OccurrenceRepository;
import com.skyNet.service.DashboardService;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImpl implements DashboardService {
    private final OccurrenceRepository occurrenceRepository;
    private final CameraRepository cameraRepository;

    public DashboardServiceImpl(OccurrenceRepository occurrenceRepository, CameraRepository cameraRepository) {
        this.occurrenceRepository = occurrenceRepository;
        this.cameraRepository = cameraRepository;
    }
    private LocalDateTime[] range(LocalDateTime s, LocalDateTime e){ if(s==null&&e==null){e=LocalDateTime.now();s=e.minusHours(24);} else if(s==null){s=e.minusHours(24);} else if(e==null){e=s.plusHours(24);} if(s.isAfter(e)) throw new IllegalArgumentException("startDate must be before endDate"); return new LocalDateTime[]{s,e};}
    private List<Occurrence> filtered(LocalDateTime s, LocalDateTime e, Long cameraId){ var r=range(s,e); return occurrenceRepository.findAll().stream().filter(o->o.getDate()!=null).filter(o->!o.getDate().atStartOfDay().isBefore(r[0])&&!o.getDate().atStartOfDay().isAfter(r[1])).filter(o->cameraId==null||(o.getCamera()!=null&&cameraId.equals(o.getCamera().getId()))).toList(); }
    private String sev(Occurrence o){ double v=o.getReliable()==null?0:o.getReliable(); if(v>=0.8) return "LOW"; if(v>=0.5) return "MEDIUM"; return "CRITICAL"; }
    public DashboardSummaryDTO getSummary(LocalDateTime s, LocalDateTime e, Long cameraId, Long regionId){ var list=filtered(s,e,cameraId); long c=list.stream().filter(o->"CRITICAL".equals(sev(o))).count(); long m=list.stream().filter(o->"MEDIUM".equals(sev(o))).count(); long l=list.stream().filter(o->"LOW".equals(sev(o))).count(); long active=cameraRepository.countByActive(true); long totalCam=cameraRepository.count(); Double avg=list.stream().map(Occurrence::getReliable).filter(Objects::nonNull).mapToDouble(Double::doubleValue).average().orElse(0); LocalDateTime last=list.stream().map(Occurrence::getDate).filter(Objects::nonNull).max(LocalDate::compareTo).map(LocalDate::atStartOfDay).orElse(null); return new DashboardSummaryDTO((long)list.size(),c,m,l,active,totalCam-active,avg,null,"UNKNOWN",null,last); }
    public List<EventTypeChartDTO> getEventsByType(LocalDateTime s, LocalDateTime e, Long cameraId, Long regionId, String severity){ var list=filtered(s,e,cameraId); if(severity!=null) list=list.stream().filter(o->severity.equalsIgnoreCase(sev(o))).toList(); Map<String,Long> m=list.stream().collect(Collectors.groupingBy(o->Optional.ofNullable(o.getDescription()).orElse("UNKNOWN"),Collectors.counting())); long total=list.size(); return m.entrySet().stream().map(x->new EventTypeChartDTO(x.getKey(),x.getKey(),x.getValue(),total==0?0:(x.getValue()*100.0/total))).toList(); }
    public List<EventTimelineDTO> getEventsTimeline(LocalDateTime s, LocalDateTime e, DashboardGroupBy g, Long cameraId, Long regionId, String eventType, String severity){ var list=filtered(s,e,cameraId); Map<String,List<Occurrence>> grp=list.stream().collect(Collectors.groupingBy(o->{LocalDate d=o.getDate(); return switch(g){case HOUR->d.toString()+" 00";case WEEK->d.getYear()+"-W"+d.get(WeekFields.ISO.weekOfWeekBasedYear());case MONTH->d.getYear()+"-"+d.getMonthValue();default->d.toString();};})); return grp.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(e1->{long c=e1.getValue().stream().filter(o->"CRITICAL".equals(sev(o))).count(); long m=e1.getValue().stream().filter(o->"MEDIUM".equals(sev(o))).count(); long l=e1.getValue().stream().filter(o->"LOW".equals(sev(o))).count(); return new EventTimelineDTO(e1.getKey(),(long)e1.getValue().size(),c,m,l);}).toList(); }
    public List<RecentEventDTO> getRecentEvents(Integer limit, String severity, Long cameraId, Long regionId){ int lim=Math.min(limit==null?10:limit,100); return occurrenceRepository.findAll().stream().sorted(Comparator.comparing(Occurrence::getDate,Comparator.nullsLast(Comparator.naturalOrder())).reversed()).filter(o->severity==null||severity.equalsIgnoreCase(sev(o))).limit(lim).map(o->new RecentEventDTO(o.getId(),"UNKNOWN","UNKNOWN",sev(o),o.getReliable(),o.getCamera()==null?null:o.getCamera().getId(),o.getCamera()==null?null:o.getCamera().getName(),null,null,o.getDate()==null?null:o.getDate().atStartOfDay(),"OPEN",false,o.getDescription(),o.getDescription())).toList(); }
    public List<CriticalAlertDTO> getCriticalAlerts(String status, LocalDateTime startDate, LocalDateTime endDate, Integer limit){ return List.of(); }
    public List<CameraStatusDTO> getCameraStatus(){ return cameraRepository.findAll().stream().map(c->new CameraStatusDTO(c.getId(),c.getName(),null,Optional.ofNullable(c.getStatus()).orElse(c.getActive()?"ONLINE":"OFFLINE"),null,null,0L,0L,0.0,null,c.getActive()?85.0:20.0)).toList(); }
    public List<HeatmapPointDTO> getHeatmap(LocalDateTime startDate, LocalDateTime endDate, String eventType, String severity, Long regionId){ return occurrenceRepository.findAll().stream().filter(o->o.getCamera()!=null).collect(Collectors.groupingBy(o->o.getCamera().getId())).values().stream().map(l->{Occurrence o=l.get(0); Camera c=o.getCamera(); long critical=l.stream().filter(x->"CRITICAL".equals(sev(x))).count(); double w=l.stream().mapToDouble(x->switch(sev(x)){case "LOW"->1;case "MEDIUM"->3;default->5;}).sum(); return new HeatmapPointDTO(0.0,0.0,w,(long)l.size(),critical,null,null,c.getId(),c.getName());}).toList(); }
    public List<RegionRiskDTO> getRegionsRiskRanking(LocalDateTime startDate, LocalDateTime endDate){ return List.of(new RegionRiskDTO(null,"GLOBAL",occurrenceRepository.count(),0L,occurrenceRepository.calcularMediaConfiabilidade(),0.0,"LOW","UNKNOWN")); }
    public OperationalKpiDTO getOperationalKpis(LocalDateTime startDate, LocalDateTime endDate){ long events=occurrenceRepository.count(); return new OperationalKpiDTO(events*30,events,events==0?0:events/(double)(events*30),null,occurrenceRepository.calcularMediaConfiabilidade(),0.0,0.0,null,0L,null,null); }
    public Map<String, Object> getHumanReview(LocalDateTime startDate, LocalDateTime endDate, Long reviewerId){ return Map.of("totalReviewed",0,"pendingReview",occurrenceRepository.count()); }
    public Map<String, Object> getPrivacyCompliance(){ return Map.of("totalAnonymizedEvents",0,"eventsWithoutAnonymizedSnapshot",occurrenceRepository.count()); }
    public Map<String, Object> getModelPerformance(LocalDateTime startDate, LocalDateTime endDate, String modelVersion){ return Map.of("totalDetections",occurrenceRepository.count(),"averageConfidence",Optional.ofNullable(occurrenceRepository.calcularMediaConfiabilidade()).orElse(0.0),"todo","Model telemetry not available in current schema"); }
}
