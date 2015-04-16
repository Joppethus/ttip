package com.searcheveryaspect.backend.webserver.controller;

import com.google.common.collect.ImmutableList;

import com.searcheveryaspect.backend.webserver.SearchResponse;
import com.searcheveryaspect.backend.webserver.SearchResponse.Party;
import com.searcheveryaspect.backend.webserver.SearchResponse.Party.Interval;
import com.searcheveryaspect.backend.webserver.SearchResponse.Party.Interval.Document;


/**
 * Provides some utils for mocking up search responses.
 */
public class SearchResponseUtil {
  // Only used for testing purposes til access to Elasticsearch is fixed.
  public static SearchResponse dummyMonthResponse(String category) {

    Document doc1 =
        new Document("med anledning av prop. 2014/15:71 Förenklingar i anläggningslagen",
            "http://data.riksdagen.se/dokument/H2023059.html");
    Document doc2 =
        new Document(
            "med anledning av prop. 2014/15:85 Ökad individanpassning – en effektivare sfi och vuxenutbildning",
            "http://data.riksdagen.se/dokument/H2023051.html");

    ImmutableList<Interval> interval1 =
        new ImmutableList.Builder<Interval>()
            .add(new Interval(2, new ImmutableList.Builder<Document>().add(doc1).add(doc2).build()))
            .add(
                new Interval(4, new ImmutableList.Builder<Document>().add(doc1).add(doc1).add(doc1)
                    .add(doc2).build()))
            .add(new Interval(1, new ImmutableList.Builder<Document>().add(doc2).build())).build();
    ImmutableList<Interval> interval2 =
        new ImmutableList.Builder<Interval>()
            .add(new Interval(0, new ImmutableList.Builder<Document>().build()))
            .add(new Interval(2, new ImmutableList.Builder<Document>().add(doc1).add(doc1).build()))
            .add(
                new Interval(3, new ImmutableList.Builder<Document>().add(doc2).add(doc1).add(doc2)
                    .build())).build();
    ImmutableList<Interval> interval3 =
        new ImmutableList.Builder<Interval>()
            .add(
                new Interval(5, new ImmutableList.Builder<Document>().add(doc1).add(doc2).add(doc1)
                    .add(doc2).add(doc2).build()))
            .add(new Interval(2, new ImmutableList.Builder<Document>().add(doc2).add(doc2).build()))
            .add(new Interval(2, new ImmutableList.Builder<Document>().add(doc2).add(doc1).build()))
            .build();
    ImmutableList<Interval> interval4 =
        new ImmutableList.Builder<Interval>()
            .add(new Interval(0, new ImmutableList.Builder<Document>().build()))
            .add(new Interval(0, new ImmutableList.Builder<Document>().build()))
            .add(new Interval(0, new ImmutableList.Builder<Document>().build())).build();

    ImmutableList<String> labels =
        new ImmutableList.Builder<String>().add("2015-02").add("2015-03").add("2015-04").build();
    ImmutableList<SearchResponse.Party> dataset =
        new ImmutableList.Builder<Party>().add(new Party("V", interval1))
            .add(new Party("S", interval2)).add(new Party("MP", interval3))
            .add(new Party("SD", interval1)).add(new Party("NYD", interval4))
            .add(new Party("C", interval3)).add(new Party("FP", interval1))
            .add(new Party("KD", interval2)).add(new Party("M", interval3)).build();
    return SearchResponse.newSearchAggregateResponse().category(category).labels(labels)
        .datasets(dataset).build();
  }
}