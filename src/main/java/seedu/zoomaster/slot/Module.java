package seedu.zoomaster.slot;

import seedu.zoomaster.bookmark.Bookmark;
import seedu.zoomaster.bookmark.BookmarkList;
import seedu.zoomaster.exception.ZoomasterException;
import seedu.zoomaster.exception.ZoomasterExceptionType;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//@@author xingrong123
public class Module {
    public static final String EMPTY_BOOKMARKLIST_MSG = "no bookmarks found in module";
    public static final String NO_BOOKMARKS_IN_SLOT = "  no bookmarks found in slot";
    private String moduleCode;
    private BookmarkList bookmarks;
    private List<Slot> slots;
    private static ArrayList<String> moduleList; //List of all NUS module codes

    public static ArrayList<String> getModuleList() {
        return moduleList;
    }

    public static void setModuleList(ArrayList<String> moduleList) {
        Module.moduleList = moduleList;
    }

    public Module(String moduleCode) {
        this.moduleCode = moduleCode.toUpperCase();
        bookmarks = new BookmarkList();
        slots = new ArrayList<>();
    }

    public void addSlot(Slot slot) {
        slots.add(slot);

        Collections.sort(slots, new Comparator<Slot>() {
            @Override
            public int compare(Slot s1, Slot s2) {
                return dayAndTimeComparator(s1, s2);
            }
        });
    }

    private static int dayAndTimeComparator(Slot s1, Slot s2) {
        int compareValue = Day.getDayValue(s1.getDay()) - Day.getDayValue(s2.getDay());
        if (compareValue == 0) {
            compareValue = s1.getStartTime().compareTo(s2.getStartTime());
        }
        return compareValue;
    }

    public boolean slotExists(String lesson, String day, LocalTime startTime, LocalTime endTime) {
        for (Slot slot : slots) {
            if (slot.match(lesson, day, startTime, endTime)) {
                return true;
            }
        }
        return false;
    }

    public boolean slotExists(Slot slot) {
        return slots.contains(slot);
    }

    public Slot getSlot(String lesson, String day, LocalTime startTime, LocalTime endTime) {
        for (Slot slot : slots) {
            if (slot.match(lesson, day, startTime, endTime)) {
                return slot;
            }
        }
        return null;
    }

    public Slot getSlot(int index) throws ZoomasterException {
        Slot slot;
        if (slots.size() == 0) {
            throw new ZoomasterException(ZoomasterExceptionType.ZERO_SLOTS_IN_MODULE);
        }
        try {
            slot = slots.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new ZoomasterException(ZoomasterExceptionType.INVALID_SLOT_NUMBER, "" + slots.size());
        }
        return slot;
    }

    public void removeSlot(Slot slot) {
        assert slots.contains(slot) : "Use getSlot to get reference of slot to be deleted before calling this method";
        slots.remove(slot);
    }

    public boolean isModule(String moduleCode) {
        boolean isModule = false;
        if (this.moduleCode.compareToIgnoreCase(moduleCode) == 0) {
            isModule = true;
        }
        return isModule;
    }

    public void addBookmark(Bookmark bookmark) {
        bookmarks.addBookmark(bookmark);
    }

    public void removeAllBookmarks() {
        bookmarks = new BookmarkList();
    }

    public List<Slot> getSlotList() {
        return slots;
    }

    public String getBookmarksToString() {
        String message = "";
        List<Bookmark> bookmarkList = bookmarks.getBookmarks();
        for (Bookmark bookmark : bookmarkList) {
            message += bookmark.getBookmarkAsString();
        }
        if (!message.isBlank()) {
            message += System.lineSeparator();
        }
        if (bookmarkList.isEmpty()) {
            message += EMPTY_BOOKMARKLIST_MSG + System.lineSeparator() + System.lineSeparator();
        }
        for (int i = 0; i < slots.size(); i++) {
            Slot slot = slots.get(i);
            message += (i + 1) + ". " + slot.getDay() + " " + slot.toString() + System.lineSeparator();
            List<Bookmark> slotBookmarkList = slot.getBookmarkList().getBookmarks();
            for (Bookmark bookmark : slotBookmarkList) {
                message += "  " + bookmark.getBookmarkAsString();
            }
            if (slotBookmarkList.isEmpty()) {
                message += NO_BOOKMARKS_IN_SLOT + System.lineSeparator();
            }
            message += System.lineSeparator();
        }
        return message;
    }

    public String launchBookmarks() {
        String message = bookmarks.launchAllBookmarks();
        return message;
    }

    public String getModuleCode() {
        return moduleCode;
    }
}
