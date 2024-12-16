"use client";

import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { signIn, signOut, useSession } from "next-auth/react";
import Image from "next/image";
import Link from "next/link";

export default function Home() {
  const { data: session, status } = useSession()

  function logout() {
    signOut();
  }

  function login() {
    signIn("keycloak")
  }

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
      <div className="p-8 bg-white rounded-lg shadow-md">
        <h1 className="mb-6 text-3xl font-bold text-center">Welcome to Our App</h1>
        {status === "authenticated" && session.user ? (
          <div className="flex flex-col items-center space-y-4">
            <Avatar className="w-20 h-20">
              <AvatarFallback className="text-2xl">
                {session.user.name?.[0].toUpperCase()}
              </AvatarFallback>
            </Avatar>
            <p className="text-xl font-semibold">{session.user.name}</p>
            <p className="text-gray-600">{session.user.email}</p>
            <Button onClick={logout}>Logout</Button>
            <Link href={"/books"}>
              <Button>View Books</Button>
            </Link>
          </div>
        ) : (
          <div className="flex flex-col items-center space-y-4">
            <p className="text-xl">Please sign in to continue</p>
            <Button onClick={login}>Login</Button>
          </div>
        )}
      </div>
    </div>
  );
}
