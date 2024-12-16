import NextAuth from "next-auth";
import KeyCloakProvider from "next-auth/providers/keycloak";

const handler = NextAuth({
  providers: [
    KeyCloakProvider({
      clientId: process.env.KEYCLOAK_CLIENT_ID!!,
      clientSecret: process.env.KEYCLOAK_CLIENT_SECRET!!,
      issuer: process.env.JWT_ISSUER_URI!!
    })
  ]
});

export { handler as GET, handler as POST};
