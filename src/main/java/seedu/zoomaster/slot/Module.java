package seedu.zoomaster.slot;

import seedu.zoomaster.bookmark.Bookmark;
import seedu.zoomaster.bookmark.BookmarkList;
import seedu.zoomaster.exception.ZoomasterException;
import seedu.zoomaster.exception.ZoomasterExceptionType;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Module {
    private String moduleCode;
    private BookmarkList bookmarks;
    private List<Slot> slots;

    public static ArrayList<String> getModuleList() {
        return moduleList;
    }

    public static void setModuleList(ArrayList<String> moduleList) {
        Module.moduleList = moduleList;
    }

    private static ArrayList<String> moduleList;


    public Module(String moduleCode) {
        this.moduleCode = moduleCode.toUpperCase();
        bookmarks = new BookmarkList();
        slots = new ArrayList<>();
    }

    public void addSlot(Slot slot) {
        slots.add(slot);
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
        try {
            slot = slots.get(index);
        } catch (IndexOutOfBoundsException e) {
            throw new ZoomasterException(ZoomasterExceptionType.INVALID_SLOT_NUMBER, "" + slots.size());
        }
        return slot;
    }

    public Slot createSlotNew(String lesson, String day, LocalTime startTime, LocalTime endTime) 
        throws ZoomasterException {
        Slot slot = new Slot(startTime, endTime, day, lesson);
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

    public String getBookmarks() {
        String message = "";
        List<Bookmark> bookmarkList = bookmarks.getBookmarkList();
        for (Bookmark bookmark : bookmarkList) {
            message += bookmark.getBookmarkAsString() + "\n";
        }
        if (bookmarkList.isEmpty()) {
            message += "no bookmarks found in module\n\n";
        }
        for (Slot slot : slots) {
            message += slot.toString() + "\n";
            List<Bookmark> slotBookmarkList = slot.getBookmarkList();
            for (Bookmark bookmark : slotBookmarkList) {
                message += "  " + bookmark.getBookmarkAsString() + "\n";
            }
            if (slotBookmarkList.isEmpty()) {
                message += "  no bookmarks found in slot\n";
            }
        }
        if (message.isBlank()) {
            message += "no bookmarks found in " + moduleCode + "\n";
        }
        return message;
    }

    public String getModuleCode() {
        return moduleCode;
    }
}
