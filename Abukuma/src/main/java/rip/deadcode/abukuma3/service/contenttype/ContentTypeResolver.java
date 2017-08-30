package rip.deadcode.abukuma3.service.contenttype;

public interface ContentTypeResolver {

    static final ContentTypeResolver resolver = new DefaultContentTypeResolver();

    // TODO
    public static ContentTypeResolver getInstance() {
        return resolver;
    }

    public String getContentType(String filename);

}
