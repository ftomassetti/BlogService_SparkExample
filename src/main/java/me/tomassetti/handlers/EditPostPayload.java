package me.tomassetti.handlers;

import lombok.Data;
import me.tomassetti.Validable;

import java.util.LinkedList;
import java.util.List;

@Data
class EditPostPayload implements Validable {
    private String title;
    private List<String> categories = new LinkedList<>();
    private String content;

    /**
     * <p>isValid.</p>
     *
     * @return a boolean.
     */
    public boolean isValid() {
        return true;
    }
}
