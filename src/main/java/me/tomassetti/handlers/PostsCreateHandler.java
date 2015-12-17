package me.tomassetti.handlers;

import me.tomassetti.AbstractRequestHandler;
import me.tomassetti.Answer;
import me.tomassetti.model.Model;

import java.util.Map;
import java.util.UUID;

/**
 * <p>PostsCreateHandler class.</p>
 *
 * @author ftomassetti
 * @version 1:0
 */
public class PostsCreateHandler extends AbstractRequestHandler<NewPostPayload> {

    private Model model;

    /**
     * <p>Constructor for PostsCreateHandler.</p>
     *
     * @param model a {@link me.tomassetti.model.Model} object.
     */
    public PostsCreateHandler(Model model) {
        super(NewPostPayload.class, model);
        this.model = model;
    }

    /** {@inheritDoc} */
    @Override
    protected Answer processImpl(NewPostPayload value, Map<String, String> urlParams, boolean shouldReturnHtml) {
        UUID id = model.createPost(value.getTitle(), value.getContent(), value.getCategories());
        return new Answer(201, id.toString());
    }
}
