package io.jsonwebtoken.security;

import javax.crypto.SecretKey;

/**
 * A {@code Key} suitable for use with password-based key derivation algorithms.
 *
 * <h4>Usage Warning</h4>
 * <p>Because raw passwords should never be used as direct inputs for cryptographic operations (such as authenticated
 * hashing or encryption) - and only for derivation algorithms (like password-based encryption) - {@code PasswordKey}
 * instances will throw an exception when used in these invalid contexts.  Specifically, calling a
 * {@code PasswordKey}'s {@link PasswordKey#getEncoded() getEncoded()} method (as would be done automatically by the
 * JCA subsystem during direct cryptographic operations) will throw an
 * {@link UnsupportedOperationException UnsupportedOperationException}.</p>
 *
 * <h4>Password Safety</h4>
 * <p>Instances returned by this method directly share the specified {@code password} character array argument -
 * changes to that char array will be reflected in the returned key, and similarly, any call to the key's
 * {@link PasswordKey#destroy() destroy()} method will clear/overwrite the shared char array. This is to ensure that
 * any clearing of the source password char array for security/safety reasons also guarantees the key is also
 * cleared and vice versa.  However, as is standard for JCA keys, calling {@link #getPassword() getPassword()} will
 * return a separate independent clone of the underlying character array.</p>
 *
 * @see #getPassword()
 * @since JJWT_RELEASE_VERSION
 */
public interface PasswordKey extends SecretKey {

    /**
     * Returns a clone of the underlying password character array represented by this Key.  Like all
     * {@code SecretKey} implementations, if you wish to clear the backing password character array for
     * safety/security reasons, call the Key's {@link #destroy()} method, ensuring that both the password is cleared
     * and the key instance can no longer be used.
     * <h4>Usage</h4>
     * <p>Because a clone is returned from this method, it is expected that callers will clear the resulting clone from
     * memory as soon as possible to reduce password exposure.  For example:
     * <pre><code>
     * char[] clonedPassword = aPasswordKey.getPassword();
     * try {
     *     doSomethingWithPassword(clonedPassword);
     * } finally {
     *     // guarantee clone is cleared regardless of any Exception thrown:
     *     java.util.Arrays.fill(clonedPassword, '\u0000');
     * }
     * </code></pre>
     * </p>
     *
     * @return a clone of the underlying password character array represented by this Key.
     */
    char[] getPassword();
}
