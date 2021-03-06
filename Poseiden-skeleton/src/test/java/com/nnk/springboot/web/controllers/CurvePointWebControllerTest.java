package com.nnk.springboot.web.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CurvePointWebControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CurvePointService service;

  private final CurvePoint curve= new CurvePoint();
  private final CurvePoint curve1= new CurvePoint();

  @BeforeEach
  void setUp() {
    curve.setId(1);
    curve.setCurveId(10);
    curve.setTerm(12);
    curve.setValue(3);

    curve1.setId(2);
    curve1.setCurveId(20);
    curve1.setTerm(14);
    curve1.setValue(4);
  }

  @Test
  void givenTwoCurvePointWhenCurveHomeThenCurveHomeTableWithTwoElementsDisplayed() throws Exception {
    //Given
    when(service.getAll()).thenReturn(List.of(curve,curve1));

    //When
    mockMvc.perform(get("/curvePoint/list"))
        .andExpect(view().name("curvePoint/list"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("1")))
        .andExpect(content().string(containsString("10")))
        .andExpect(content().string(containsString("12")))
        .andExpect(content().string(containsString("3")))

        .andExpect(content().string(containsString("2")))
        .andExpect(content().string(containsString("20")))
        .andExpect(content().string(containsString("14")))
        .andExpect(content().string(containsString("4")));
  }

  @Test
  void showCurvePointForm() throws Exception {
    //When
    mockMvc.perform(get("/curvePoint/add"))
        .andExpect(view().name("curvePoint/add"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("Curve Id")))
        .andExpect(content().string(containsString("Term")))
        .andExpect(content().string(containsString("Value")))

        .andExpect(content().string(containsString("Cancel")))
        .andExpect(content().string(containsString("Add CurvePoint")));
  }

  @Test
  void givenANewCurvePointValidWhenCreateThenCurveIsSavedAndCurveHomeDisplayed() throws Exception {
    //Given
    when(service.create(any())).thenReturn(curve);
    when(service.getAll()).thenReturn(List.of(curve));

    //When
    mockMvc.perform(post("/curvePoint/validate")
        .param("curveId","10")
        .param("term","12")
        .param("value","3"))
        .andExpect(view().name("curvePoint/list"))
        .andExpect(status().isCreated())

        .andExpect(content().string(containsString("1")))
        .andExpect(content().string(containsString("10")))
        .andExpect(content().string(containsString("12")))
        .andExpect(content().string(containsString("3")));
  }

  @Test
  void givenANewCurvePointNotValidWhenCreateThenCurveFormDisplayedWithErrorMessage() throws Exception {
    //When
    mockMvc.perform(post("/curvePoint/validate"))
        .andExpect(view().name("curvePoint/add"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("Curve Id must not be null")))
        .andExpect(content().string(containsString("Term must be minimum equal to 0.1")))
        .andExpect(content().string(containsString("Value must not be null")));
  }
  @Test
  void showUpdateForm() throws Exception {
    //Given
    when(service.getById(anyInt())).thenReturn(curve);

    //When
    mockMvc.perform(get("/curvePoint/update/1")
        .param("curveId","10")
        .param("term","12")
        .param("value","3"))
        .andExpect(view().name("curvePoint/update"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("Curve Id")))
        .andExpect(content().string(containsString("10")))

        .andExpect(content().string(containsString("Term")))
        .andExpect(content().string(containsString("12")))

        .andExpect(content().string(containsString("Value")))
        .andExpect(content().string(containsString("3")));
  }

  @Test
  void givenAExistingCurvePointWhenValidUpdateThenCurveIsUpdatedAndCurveHomeDisplayed() throws Exception {
    //Given
    when(service.update(anyInt(),any())).thenReturn(curve);
    when(service.getAll()).thenReturn(List.of(curve));

    //When
    mockMvc.perform(post("/curvePoint/update/1")
        .param("curveId","10")
        .param("Term","12")
        .param("value","3"))
        .andExpect(view().name("curvePoint/list"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("1")))
        .andExpect(content().string(containsString("10")))
        .andExpect(content().string(containsString("12")))
        .andExpect(content().string(containsString("3")));
  }

  @Test
  void givenAExistingCurvePointWhenNotValidUpdateThenCurveUpdateFormDisplayedWithErrorMessage() throws Exception {
    //When
    mockMvc.perform(post("/curvePoint/update/1"))
        .andExpect(view().name("curvePoint/update"))
        .andExpect(status().isOk())

        .andExpect(content().string(containsString("Curve Id must not be null")))
        .andExpect(content().string(containsString("Term must be minimum equal to 0.1")))
        .andExpect(content().string(containsString("Value must not be null")));
  }

  @Test
  void givenACurvePointWhenDeleteCurveThenCurvePointIsDeletedAndCurveHomeDisplayed() throws Exception {
    //When
    mockMvc.perform(get("/curvePoint/delete/1"))
        .andExpect(view().name("curvePoint/list"))
        .andExpect(status().isOk());
  }
}