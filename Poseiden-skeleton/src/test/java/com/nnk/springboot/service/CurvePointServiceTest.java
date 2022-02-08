package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class CurvePointServiceTest {
  private final CurvePointRepository repository= mock(CurvePointRepository.class);
  private final CurvePointService classUnderTest= new CurvePointServiceImpl(repository);

  private final CurvePoint curve= new CurvePoint();
  private final CurvePoint curve1= new CurvePoint();

  private final CurvePoint curveToSave= new CurvePoint();

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

    curveToSave.setCurveId(10);
    curveToSave.setTerm(12);
    curveToSave.setValue(3);
  }

  @Test
  void givenTwoCurvePointWhenGetAllThenReturnTwoResults() {
    //Given
    when(repository.findAll()).thenReturn(List.of(curve,curve1));

    //When
    List<CurvePoint>actual= classUnderTest.getAll();

    //Then
    assertThat(actual.size()).isEqualTo(2);
    verify(repository,times(1)).findAll();
  }

  @Test
  void givenACurvePointValidWhenCreateThenCurvePointIsSaved() {
    //Given
    when(repository.save(any())).thenReturn(curve);

    //When
    CurvePoint actual= classUnderTest.create(curveToSave);

    //Then
    assertSame(actual,curve);
    verify(repository,times(1)).save(curveToSave);
  }

  @Test
  void givenAExistingCurvePointWhenGetByIdThenCurveIsFounded() {
    //Given
    when(repository.getById(anyInt())).thenReturn(curve);

    //When
    CurvePoint actual= classUnderTest.getById(1);

    //Then
    assertSame(actual,curve);
    verify(repository,times(1)).getById(1);
  }
}