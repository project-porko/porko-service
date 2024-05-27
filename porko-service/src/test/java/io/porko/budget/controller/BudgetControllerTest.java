package io.porko.budget.controller;

import io.porko.budget.controller.model.BudgetResponse;
import io.porko.budget.service.BudgetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.YearMonth;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(BudgetControllerTest.class)
@DisplayName("Controller:Budget")
class BudgetControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BudgetService budgetService;

//    @BeforeEach
//    void setUp() {
//        // BudgetService의 행위를 설정
//        when(budgetService.getBudget(any(YearMonth.class), any(Long.class)))
//                .thenReturn(new BudgetResponse(/* 예상되는 응답 데이터를 생성하여 반환 */));
//    }
//
//    @Test
//    void whenGetBudget_thenReturnsBudgetResponse() throws Exception {
//        // GET 요청 보내기
//        mockMvc.perform(get("/budget")
//                        .param("goalDate", "2024-04")
//                        .param("memberId", "1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                // 응답 검증
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$./* 검증할 필드 */").value(/* 예상되는 값 */));
//    }
}