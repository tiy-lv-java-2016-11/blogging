package com.theironyard.CommandObjects;

/**
 * Created by darionmoore on 1/14/17.
 */
public class EntryCommand {
    private String entryTitle;
    private String entryDescription;
    private String entry;


    public String getEntryTitle() {
        return entryTitle;
    }

    public void setEntryTitle(String entryTitle) {
        this.entryTitle = entryTitle;
    }

    public String getEntryDescription() {
        return entryDescription;
    }

    public void setEntryDescription(String entryDescription) {
        this.entryDescription = entryDescription;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }
}
