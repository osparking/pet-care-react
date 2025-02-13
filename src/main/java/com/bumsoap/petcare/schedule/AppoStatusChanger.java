package com.bumsoap.petcare.schedule;

import com.bumsoap.petcare.service.appointment.IServiceAppointment;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class AppoStatusChanger {
    private final IServiceAppointment appointService;

    /** 실행 빈도 지정 항목 설명
     * 0 : 해당 분, 최초(0초)
     * 0/2 : 해당 시각의 최초 부터, 2분 간격
     * '*' : 해당 일의 매 시간(=시각)
     * 1/1 : 해당 월의 매 일
     * '*' : 매 월
     * '?' : 각 주의 매 요일
     */
    @Scheduled(cron = "0 0/2 * 1/1 * ?")
    public void automateAppointStatusChange() {
        List<Long> appointIds = appointService.appointIds();
        for (Long appointId : appointIds) {
            appointService.setAppointStatus(appointId);
        }
    }
}
