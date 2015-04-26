package com.searcheveryaspect.backend.webserver;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import java.util.Objects;

/**
 * 
 */
public class Party {
  private final String party;
  private final ImmutableList<Entry> data;
  private final Boolean isIntersting;

  public Party(String party, ImmutableList<Entry> data) {
    this(party, data, null);
  }

  public Party(String party, ImmutableList<Entry> data, Boolean isInteresting) {
    this.party = party;
    this.data = data;
    this.isIntersting = isInteresting;
  }

  public String getParty() {
    return party;
  }

  public ImmutableList<Entry> getData() {
    return data;
  }

  public Boolean getIsIntersting() {
    return isIntersting;
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof Party) {
      Party that = (Party) o;
      return party.equals(that.party) && data.equals(that.data);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(party, data);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this).add("party", party).add("data", data).toString();
  }
}
