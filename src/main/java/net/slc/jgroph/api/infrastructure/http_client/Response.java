package net.slc.jgroph.api.infrastructure.http_client;

public class Response
{
    private final int responseCode;
    private final String contentType;
    private final String body;

    public Response(final int responseCode, final String contentType, final String body)
    {
        this.responseCode = responseCode;
        this.contentType = contentType;
        this.body = body;
    }

    public int getResponseCode()
    {
        return responseCode;
    }

    public String getContentType()
    {
        return contentType;
    }

    public String getBody()
    {
        return body;
    }
}