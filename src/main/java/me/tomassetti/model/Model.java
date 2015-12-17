package me.tomassetti.model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * <p>Model interface.</p>
 *
 * @author ftomassetti
 * @version 1:0
 */
public interface Model {
    /**
     * <p>createPost.</p>
     *
     * @param title a {@link java.lang.String} object.
     * @param content a {@link java.lang.String} object.
     * @param categories a {@link java.util.List} object.
     * @return a {@link java.util.UUID} object.
     */
    UUID createPost(String title, String content, List<String> categories);
    /**
     * <p>createComment.</p>
     *
     * @param post a {@link java.util.UUID} object.
     * @param author a {@link java.lang.String} object.
     * @param content a {@link java.lang.String} object.
     * @return a {@link java.util.UUID} object.
     */
    UUID createComment(UUID post, String author, String content);
    /**
     * <p>getAllPosts.</p>
     *
     * @return a {@link java.util.List} object.
     */
    List<Post> getAllPosts();
    /**
     * <p>getAllCommentsOn.</p>
     *
     * @param post a {@link java.util.UUID} object.
     * @return a {@link java.util.List} object.
     */
    List<Comment> getAllCommentsOn(UUID post);
    /**
     * <p>existPost.</p>
     *
     * @param post a {@link java.util.UUID} object.
     * @return a boolean.
     */
    boolean existPost(UUID post);

    /**
     * <p>getPost.</p>
     *
     * @param uuid a {@link java.util.UUID} object.
     * @return a {@link java.util.Optional} object.
     */
    Optional<Post> getPost(UUID uuid);

    /**
     * <p>updatePost.</p>
     *
     * @param post a {@link me.tomassetti.model.Post} object.
     */
    void updatePost(Post post);

    /**
     * <p>deletePost.</p>
     *
     * @param uuid a {@link java.util.UUID} object.
     */
    void deletePost(UUID uuid);
}
