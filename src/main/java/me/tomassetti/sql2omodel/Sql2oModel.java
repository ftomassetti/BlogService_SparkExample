package me.tomassetti.sql2omodel;

import me.tomassetti.RandomUuidGenerator;
import me.tomassetti.UuidGenerator;
import me.tomassetti.model.Comment;
import me.tomassetti.model.Model;
import me.tomassetti.model.Post;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * <p>Sql2oModel class.</p>
 *
 * @author tomassetti
 * @author rajakolli
 * @version 1:0
 */
public class Sql2oModel implements Model {

    private static final String POST_UUID = "post_uuid";
    private static final String CONTENT = "content";
    private Sql2o sql2o;
    private UuidGenerator uuidGenerator;

    /**
     * <p>Constructor for Sql2oModel.</p>
     *
     * @param sql2o a {@link org.sql2o.Sql2o} object.
     */
    public Sql2oModel(Sql2o sql2o) {
        this.sql2o = sql2o;
        uuidGenerator = new RandomUuidGenerator();
    }

    /** {@inheritDoc} */
    @Override
    public UUID createPost(String title, String content, List<String> categories) {
        try (Connection conn = sql2o.beginTransaction()) {
            UUID postUuid = uuidGenerator.generate();
            conn.createQuery("insert into posts(post_uuid, title, content, publishing_date) VALUES (:post_uuid, :title, :content, :date)")
                    .addParameter(POST_UUID, postUuid)
                    .addParameter("title", title)
                    .addParameter(CONTENT, content)
                    .addParameter("date", new Date())
                    .executeUpdate();
            categories.forEach(category ->
                    conn.createQuery("insert into posts_categories(post_uuid, category) VALUES (:post_uuid, :category)")
                    .addParameter(POST_UUID, postUuid)
                    .addParameter("category", category)
                    .executeUpdate());
            conn.commit();
            return postUuid;
        }
    }

    /** {@inheritDoc} */
    @Override
    public UUID createComment(UUID post, String author, String content) {
        try (Connection conn = sql2o.open()) {
            UUID commentUuid = uuidGenerator.generate();
            conn.createQuery("insert into comments(comment_uuid, post_uuid, author, content, approved, submission_date) VALUES (:comment_uuid, :post_uuid, :author, :content, :approved, :date)")
                    .addParameter("comment_uuid", commentUuid)
                    .addParameter(POST_UUID, post)
                    .addParameter("author", author)
                    .addParameter(CONTENT, content)
                    .addParameter("approved", false)
                    .addParameter("date", new Date())
                    .executeUpdate();
            return commentUuid;
        }
    }

    /** {@inheritDoc} */
    @Override
    public List<Post> getAllPosts() {
        try (Connection conn = sql2o.open()) {
            List<Post> posts = conn.createQuery("select * from posts")
                    .executeAndFetch(Post.class);
            posts.forEach(post -> post.setCategories(getCategoriesFor(conn, post.getPost_uuid())));
            return posts;
        }
    }

    private List<String> getCategoriesFor(Connection conn, UUID post_uuid) {
        return conn.createQuery("select category from posts_categories where post_uuid=:post_uuid")
                .addParameter(POST_UUID, post_uuid)
                .executeAndFetch(String.class);
    }

    /** {@inheritDoc} */
    @Override
    public List<Comment> getAllCommentsOn(UUID post) {
        try (Connection conn = sql2o.open()) {
            return conn.createQuery("select * from comments where post_uuid=:post_uuid")
                    .addParameter(POST_UUID, post)
                    .executeAndFetch(Comment.class);
        }
    }

    /** {@inheritDoc} */
    @Override
    public boolean existPost(UUID post) {
        try (Connection conn = sql2o.open()) {
            List<Post> posts = conn.createQuery("select * from posts where post_uuid=:post")
                    .addParameter("post", post)
                    .executeAndFetch(Post.class);
            return posts.isEmpty() ? false : true;
        }
    }

    /** {@inheritDoc} */
    @Override
    public Optional<Post> getPost(UUID uuid) {
        try (Connection conn = sql2o.open()) {
            List<Post> posts = conn.createQuery("select * from posts where post_uuid=:post_uuid")
                    .addParameter(POST_UUID, uuid)
                    .executeAndFetch(Post.class);
            if (null != posts && posts.isEmpty()) {
                return Optional.empty();
            } else if (posts.size() == 1) {
                return Optional.of(posts.get(0));
            } else {
                throw new RuntimeException();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void updatePost(Post post) {
        try (Connection conn = sql2o.open()) {
            conn.createQuery("update posts set title=:title, content=:content where post_uuid=:post_uuid")
                    .addParameter(POST_UUID, post.getPost_uuid())
                    .addParameter("title", post.getTitle())
                    .addParameter(CONTENT, post.getContent())
                    .executeUpdate();
        }
    }

    /** {@inheritDoc} */
    @Override
    public void deletePost(UUID uuid) {
        try (Connection conn = sql2o.open()) {
            conn.createQuery("delete from posts where post_uuid=:post_uuid")
                    .addParameter(POST_UUID, uuid)
                    .executeUpdate();
        }
    }

}
