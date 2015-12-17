package me.tomassetti.handlers;

import me.tomassetti.AbstractRequestHandler;
import me.tomassetti.Answer;
import me.tomassetti.model.Model;

import java.util.Map;
import java.util.UUID;

/**
 * <p>CommentsCreateHandler class.</p>
 *
 * @author ftomassetti
 * @version 1:0
 */
public class CommentsCreateHandler extends AbstractRequestHandler<NewCommentPayload> {

    private Model model;

    /**
     * <p>Constructor for CommentsCreateHandler.</p>
     *
     * @param model a {@link me.tomassetti.model.Model} object.
     */
    public CommentsCreateHandler(Model model) {
        super(NewCommentPayload.class, model);
        this.model = model;
    }

    /** {@inheritDoc} */
    @Override
    protected Answer processImpl(NewCommentPayload creation, Map<String, String> urlParams, boolean shouldReturnHtml) {
        UUID post = UUID.fromString(urlParams.get(":uuid"));
        if (!model.existPost(post)){
            return new Answer(400);
        }
        UUID id = model.createComment(post, creation.getAuthor(), creation.getContent());
        return Answer.ok(id.toString());
    }
}
