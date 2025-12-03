package com.skyNet.service;

import com.skyNet.dto.DashboardDTO;
import com.skyNet.repository.CameraRepository;
import com.skyNet.repository.OccurrenceRepository;
import com.skyNet.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DashboardService {

    private final UserRepository userRepository;
    private final OccurrenceRepository occurrenceRepository;
    private final CameraRepository cameraRepository;

    public DashboardService(UserRepository userRepository,
                            OccurrenceRepository occurrenceRepository,
                            CameraRepository cameraRepository) {
        this.userRepository = userRepository;
        this.occurrenceRepository = occurrenceRepository;
        this.cameraRepository = cameraRepository;
    }

    public DashboardDTO getDashboardInfo() {
        Long totalUsers = userRepository.count();
        Long totalOccurrences = occurrenceRepository.count();
        Long occurrencesToday = occurrenceRepository.countByDate(LocalDate.now());

        Double averageReliability = occurrenceRepository.calcularMediaConfiabilidade();
        if (averageReliability == null) {
            averageReliability = 0.0;
        }

        Long totalCameras = cameraRepository.count();
        Long activeCameras = cameraRepository.countByActive(true);
        Long inactiveCameras = totalCameras - activeCameras;

        return new DashboardDTO(
                totalUsers,
                totalOccurrences,
                occurrencesToday,
                averageReliability,
                totalCameras,
                activeCameras,
                inactiveCameras
        );
    }
}
