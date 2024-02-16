package com.curso.bestTravel.infraestructure.services;

import com.curso.bestTravel.infraestructure.abstract_services.ReportService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ExcelService implements ReportService {
    @Override
    public byte[] readfile() {
        return new byte[0];
    }
}
