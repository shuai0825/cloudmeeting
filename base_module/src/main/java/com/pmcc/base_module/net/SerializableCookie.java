package com.pmcc.base_module.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import okhttp3.Cookie;

/**
 * SerializableOkHttpCookies
 * Created byTamic on 2016-06-09.
 * {@link # https://github.com/NeglectedByBoss/RetrofitClient}
 */
public class SerializableCookie implements Serializable {

    private transient final Cookie cookie;
    private transient Cookie clientCookies;

    public SerializableCookie(Cookie cookie) {
        this.cookie = cookie;
    }

    public Cookie getCookie() {
        Cookie bestCookie = cookie;
        if (clientCookies != null) {
            bestCookie = clientCookies;
        }
        return bestCookie;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(cookie.name());
        out.writeObject(cookie.value());
        out.writeLong(cookie.expiresAt());
        out.writeObject(cookie.domain());
        out.writeObject(cookie.path());
        out.writeBoolean(cookie.secure());
        out.writeBoolean(cookie.httpOnly());
        out.writeBoolean(cookie.hostOnly());
        out.writeBoolean(cookie.persistent());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        String name = (String) in.readObject();
        String value = (String) in.readObject();
        long expiresAt = in.readLong();
        String domain = (String) in.readObject();
        String path = (String) in.readObject();
        boolean secure = in.readBoolean();
        boolean httpOnly = in.readBoolean();
        boolean hostOnly = in.readBoolean();
        boolean persistent = in.readBoolean();
        Cookie.Builder builder = new Cookie.Builder();
        builder = builder.name(name);
        builder = builder.value(value);
        builder = builder.expiresAt(expiresAt);
        builder = hostOnly ? builder.hostOnlyDomain(domain) : builder.domain(domain);
        builder = builder.path(path);
        builder = secure ? builder.secure() : builder;
        builder = httpOnly ? builder.httpOnly() : builder;
        clientCookies =builder.build();
    }
}