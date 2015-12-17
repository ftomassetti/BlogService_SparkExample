package me.tomassetti;

/**
 * <p>Answer class.</p>
 *
 * @author ftomassetti
 * @author rajakolli
 * @version 1:0
 */
public class Answer {
    
    private int code;
    private String body;

    /**
     * <p>Constructor for Answer.</p>
     *
     * @param code a int.
     */
    public Answer(int code) {
        this.code = code;
        this.body = "";
    }
    
    /**
     * <p>Constructor for Answer.</p>
     *
     * @param code a int.
     * @param body a {@link java.lang.String} object.
     */
    public Answer(int code, String body){
        this.code = code;
        this.body = body;
    }
    
    /**
     * <p>Getter for the field <code>body</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getBody() {
        return body;
    }

    /**
     * <p>Getter for the field <code>code</code>.</p>
     *
     * @return a int.
     */
    public int getCode() {
        return code;
    }

    /** {@inheritDoc} */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answer answer = (Answer) o;

        if (code != answer.code) return false;
        if (body != null ? !body.equals(answer.body) : answer.body != null) return false;

        return true;
    }

    /** {@inheritDoc} */
    @Override
    public int hashCode() {
        int result = code;
        result = 31 * result + (body != null ? body.hashCode() : 0);
        return result;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "Answer(code=" + code + ", body=" + body + ")";
    }

    /**
     * <p>ok.</p>
     *
     * @param body a {@link java.lang.String} object.
     * @return a {@link me.tomassetti.Answer} object.
     */
    public static Answer ok(String body) {
        return new Answer(200, body);
    }
}
