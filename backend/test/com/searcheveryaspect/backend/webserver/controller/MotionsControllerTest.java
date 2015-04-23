package com.searcheveryaspect.backend.webserver.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.searcheveryaspect.backend.ESQuerier;
import com.searcheveryaspect.backend.ESRequest;
import com.searcheveryaspect.backend.webserver.SearchAggregateResponse;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.restexpress.Request;
import org.restexpress.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests for MotionsController.
 */
public class MotionsControllerTest {
  final String to = "2015-03-01";
  final String from = "2015-01-01";
  final String period = "month";
  final String category = "skatt";

  @Mock
  ESQuerier esqMock;
  @Mock
  Response resMock;
  @Mock
  Request reqMock;
  @Mock
  SearchAggregateResponse sarMock;

  // Mockito rule. Initialises mocks.
  public @Rule
  MockitoRule rule = MockitoJUnit.rule();
  // Rule for verifying that exceptions are thrown.
  public @Rule
  ExpectedException thrown = ExpectedException.none();

  @Before
  public void create() {
    // Initialise the request with valid headers.
    when(reqMock.getHeader("debug")).thenReturn(null);
    when(reqMock.getHeader("from_date")).thenReturn(from);
    when(reqMock.getHeader("to_date")).thenReturn(to);
    when(reqMock.getHeader("category")).thenReturn(category);
    when(reqMock.getHeader("interval")).thenReturn(period);
  }

  @Test
  public void testValidQuery() {
    // ESRequest for parameter matching.
    List<String> cats = new ArrayList<>();
    cats.add(category);
    ESRequest esreq =
        new ESRequest(new Interval(DateTime.parse(from, DateTimeFormat.forPattern("yyyy-mm-dd")),
            DateTime.parse(to, DateTimeFormat.forPattern("yyyy-mm-dd"))), cats, period);

    when(esqMock.fetchDocuments(esreq)).thenReturn(sarMock);

    // Controller to test behaviour on.
    MotionsController t = new MotionsController(esqMock);

    assertEquals(sarMock, t.read(reqMock, resMock));
    verify(esqMock).fetchDocuments(esreq);
    verifyNoMoreInteractions(esqMock);
  }

  @Test
  public void testNoFromDateQuery() {
    // TODO
  }

  @Test
  public void testNoNoDateQuery() {
    // TODO
  }

  @Test
  public void testNoCategoryDateQuery() {
    // TODO
  }

  @Test
  public void testNoIntervalDateQuery() {
    // TODO
  }

  @Test
  public void testWrongFromDateQuery() {
    // TODO
  }

  @Test
  public void testWrongToDateQuery() {
    // TODO
  }

  @Test
  public void testWrongCategoryDateQuery() {
    // TODO
  }

  @Test
  public void testWrongIntervalDateQuery() {
    // TODO
  }

  @Test
  public void testFromAfterBeforeQuery() {
    // Create a request with the to date taking place before the from date.
    when(reqMock.getHeader("from_date")).thenReturn(to);
    when(reqMock.getHeader("to_date")).thenReturn(from);

    // Controller to test behaviour on.
    MotionsController t = new MotionsController(esqMock);

    // Perform read attempt with invalid request, null should be returned.
    assertEquals(null, t.read(reqMock, resMock));

    ArgumentCaptor<IllegalArgumentException> captor =
        ArgumentCaptor.forClass(IllegalArgumentException.class);
    // Check that an IllegalArgumentException was set in the response.
    verify(resMock).setException(captor.capture());
    // Maybe add assertions about the exception if more are added.
    // Exception e = captor.getValue();

    // Should never be reached because of invalid parameters.
    verifyNoMoreInteractions(esqMock);
  }
}
