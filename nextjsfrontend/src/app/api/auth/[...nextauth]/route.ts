import NextAuth from "next-auth";
import KeyCloakProvider from "next-auth/providers/keycloak";

const handler = NextAuth({
  providers: [
    KeyCloakProvider({
      clientId: process.env.KEYCLOAK_CLIENT_ID!!,
      clientSecret: process.env.KEYCLOAK_CLIENT_SECRET!!,
      issuer: process.env.JWT_ISSUER_URI!!,
    })
  ],
  callbacks: {
    async jwt({token, account, profile}) {
      if (account) {
        token.access_token = account.access_token!!;
      }
      return token;
    },
    async session({session, token}) {
      session.user.id = token.sub;
      session.user.name = token.name;
      session.user.email = token.email;
      session.access_token = token.access_token;
      return session;
    }
  }
});

export { handler as GET, handler as POST};
