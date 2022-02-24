package com.nnk.springboot.api.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class CurvePointApiControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CurvePointServiceImpl service;

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
  void givenTwoCurvesSavedWhenGetAllThenCurvesWithTwoResultsAndStatus200() throws Exception {
    //Given
    when(service.getAll()).thenReturn(List.of(curve,curve1));

    //When
    mockMvc.perform(get("/api/curvePoints")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(2)))

        .andExpect(jsonPath("$[0].id",is(1)))
        .andExpect(jsonPath("$[0].curveId",is(10)))
        .andExpect(jsonPath("$[0].term",is(12.0)))
        .andExpect(jsonPath("$[0].value",is(3.0)))

        .andExpect(jsonPath("$[1].id",is(2)))
        .andExpect(jsonPath("$[1].curveId",is(20)))
        .andExpect(jsonPath("$[1].term",is(14.0)))
        .andExpect(jsonPath("$[1].value",is(4.0)));
  }

  @Test
  void givenNoCurveSavedWhenGetAllThenBidListWithNoResultsAndStatus204() throws Exception {
    //Given
    when(service.getAll()).thenReturn(List.of());

    //When
    mockMvc.perform(get("/api/curvePoints")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNoContent())
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  void givenANewValidCurveWhenCreateThenCurveIsSavedAndStatus201() throws Exception {
    //Given
    when(service.create(any())).thenReturn(curve);

    //When
    mockMvc.perform(post("/api/curvePoints")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"curveId\":10,"+
                "\"term\":12.0,"+
                "\"value\":3.0" +
                "}"))

        .andExpect(status().isCreated())

        .andExpect(jsonPath("$.id",is(1)))
        .andExpect(jsonPath("$.curveId",is(10)))
        .andExpect(jsonPath("$.term",is(12.0)))
        .andExpect(jsonPath("$.value",is(3.0)));
  }

  @Test
  void givenANewNotValidCurveWhenCreateThenCurveIsNotSavedAndStatus400() throws Exception {
    //Given
    //When
    mockMvc.perform(post("/api/curvePoints")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"curveId\":,"+
                "\"terme\":,"+
                "\"value\":" +
                "}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void givenAExistingCurveWhenUpdateThenCurveIsSavedAndStatus201() throws Exception {
    //Given
    when(service.findById(anyInt())).thenReturn(Optional.of(curve));
    when(service.update(anyInt(),any())).thenReturn(curve);

    //When
    mockMvc.perform(put("/api/curvePoints/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"curveId\":10,"+
                "\"term\":12.0,"+
                "\"value\":3.0" +
                "}"))

        .andExpect(status().isCreated())

        .andExpect(jsonPath("$.id",is(1)))
        .andExpect(jsonPath("$.curveId",is(10)))
        .andExpect(jsonPath("$.term",is(12.0)))
        .andExpect(jsonPath("$.value",is(3.0)));
  }

  @Test
  void givenANotValidCurveWhenUpdateThenCurveIsNotSavedAndStatus400() throws Exception {
    //Given
    //When
    mockMvc.perform(put("/api/curvePoints/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"curveId\":0,"+
                "\"terme\":0,"+
                "\"value\":" +
                "}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void givenANotExistCurveWhenUpdateThenCurveIsNotFoundAndStatus404() throws Exception {
    //Given
    when(service.findById(anyInt())).thenReturn(Optional.empty());

    //When
    mockMvc.perform(put("/api/curvePoints/5")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{" +
                "\"curveId\":10,"+
                "\"term\":12.0,"+
                "\"value\":3.0" +
                "}"))
        .andExpect(status().isNotFound());
  }

  @Test
  void givenAExistCurveWhenDeleteThenCurveIsDeletedAndStatus200() throws Exception {
    //Given
    when(service.findById(anyInt())).thenReturn(Optional.of(curve));
    //When
    mockMvc.perform(delete("/api/curvePoints/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void givenANotExistCurveWhenDeleteThenCurveIsNotFoundAndStatus404() throws Exception {
    //Given
    when(service.findById(anyInt())).thenReturn(Optional.empty());
    //When
    mockMvc.perform(delete("/api/curvePoints/1")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isNotFound());
  }
}