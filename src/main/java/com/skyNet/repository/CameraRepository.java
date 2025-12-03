package com.skyNet.repository;

import com.skyNet.model.Camera;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CameraRepository extends JpaRepository<Camera, Long> {

    long countByActive(Boolean active);
}
