import { GetServerSidePropsContext, NextApiRequest, NextApiResponse } from "next";
import { getServerSession, type NextAuthOptions } from "next-auth";
import KeyCloakProvider from "next-auth/providers/keycloak";
import CredentialsProvider from "next-auth/providers/credentials";
import { LoginResponse } from "./app/types/dto";


const authConfig = {
  providers: [
    KeyCloakProvider({
      clientId: process.env.KEYCLOAK_CLIENT_ID!!,
      clientSecret: process.env.KEYCLOAK_CLIENT_SECRET!!,
      issuer: process.env.JWT_ISSUER_URI!!,
    }),
    CredentialsProvider({
      name: "Credentials login",
      credentials: {
        email: { label: "Email", type: "email", placeholder: "hello@ck.com" },
        password: { label: "Password", type: "password", placeholder: "Password" }
      },
      async authorize(credentials, req) {
        const response = await fetch(`${process.env.BACKEND_URI}/umgmt/auth/login`, {
          body: JSON.stringify(credentials),
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          }
        });
        if (!response.ok) {
          const errorT = await response.text();
          console.error("Login error: ", errorT);
          console.error("Headers: ", Object.fromEntries(response.headers));
          return null;
        }
        const json: LoginResponse = await response.json();
        return {id: credentials!!.email, name: credentials!!.email, email: credentials!!.email, access_token: json.access_token};
      },
    })
  ],
  callbacks: {
    async jwt({ token, account, profile, user }) {
      if (account) {
        token.access_token = account.access_token!!;
      }
      if (user) {
        token.access_token = user.access_token!!;
      }
      return token;
    },
    async session({ session, token, user }) {
      session.user.id = token.sub;
      session.user.name = token.name;
      session.user.email = token.email;
      session.access_token = token.access_token;
      return session;
    }
  }
} satisfies NextAuthOptions;

function auth(
  ...args:
    | [GetServerSidePropsContext["req"], GetServerSidePropsContext["res"]]
    | [NextApiRequest, NextApiResponse]
    | []
) {
  return getServerSession(...args, authConfig);
}

export { authConfig, auth as serverSession };
