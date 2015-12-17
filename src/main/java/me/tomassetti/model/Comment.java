package me.tomassetti.model;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

/**
 * <p>Comment class.</p>
 *
 * @author tomassetti
 * @version 1:0
 */
@Data
public class Comment {
    UUID comment_uuid;
    UUID post_uuid;
    String author;
    String content;
    boolean approved;
    Date submission_date;
}
