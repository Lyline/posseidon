package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CurvePointServiceTest {
  private final CurvePointRepository repository= mock(CurvePointRepository.class);
  private final CurvePointService classUnderTest= new CurvePointServiceImpl(repository);

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
  void givenTwoCurvePoint() {
    //Given
    when(repository.findAll()).thenReturn(List.of(curve,curve1));

    //When
    List<CurvePoint>actual= classUnderTest.getAll();

    //Then
    assertThat(actual.size()).isEqualTo(2);
  }
}