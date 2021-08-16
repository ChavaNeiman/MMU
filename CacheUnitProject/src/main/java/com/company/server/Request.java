package main.java.com.company.server;

import java.io.Serializable;
import java.util.Map;


public class Request<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private java.util.Map<String,String> headers;
    private T body;

    public Request(java.util.Map<String,String> headers,T body) {
        this.headers = headers;
        this.body = body;
    }

    public Map<String,String> getHeaders() {
        return headers;
    }


    public void setHeaders(Map<String,String> headers)
    {this.headers=headers;}

    public T getBody()
    {return body;}

    public void setBody(T body)
    {this.body=body;}

    public String toString() {
        return "Action = " + getHeaders().toString() + " Content = " + getBody().toString();
    }

}