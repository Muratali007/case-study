package kz.diploma.kitaphub.controller;

import java.util.List;
import kz.diploma.kitaphub.data.dto.DashboardPieDto;
import kz.diploma.kitaphub.data.dto.DashboardRequestDto;
import kz.diploma.kitaphub.service.DashboardService;
import kz.diploma.kitaphub.service.DashboardService.DashboardTotalDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/dashboard")
public class DashboardController {
  private final DashboardService dashboardService;

  public DashboardController(DashboardService dashboardService) {
    this.dashboardService = dashboardService;
  }

  @GetMapping("/total")
  public DashboardTotalDto getTotals() {
    return dashboardService.getTotals();
  }

  @GetMapping("/area")
  public List<DashboardRequestDto> getArea() {
    return dashboardService.getAreaChart();
  }

  @GetMapping("/bar")
  public List<DashboardRequestDto> getBar() {
    return dashboardService.getBarChart();
  }

  @GetMapping("/pie")
  public DashboardPieDto getPie() {
    return dashboardService.getPieChart();
  }
}
