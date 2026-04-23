package com.medilabo.frontend.service;

import reactor.core.publisher.Mono;

public interface RiskService {
    String getRiskByPatientId(Long patientId);
}