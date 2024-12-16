"use client";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";
import { Button } from "@/components/ui/button";
import { Session } from "next-auth";
import { signOut, useSession } from "next-auth/react";
import Link from "next/link";
import { redirect, usePathname } from "next/navigation";
import React from "react";

const pathNameToPageTitle = {
  "/books": "Available Books",
  "/history": "Borrow History",
  "/admin": "Admin"
}

function NavBar({ session }: { session: Session }) {
  const pathName = usePathname() as "/books" | "/history" | "/admin";

  function logout() {
    signOut();
  }

  return (
    <>
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-2xl font-bold">{pathNameToPageTitle[pathName]}</h1>
        <div className="flex items-center space-x-4">
          {pathName === "/books" && (<Link href="/history"><Button variant="link">History</Button></Link>)}
          {pathName === "/history" && (<Link href="/books"><Button variant="link">Books</Button></Link>)}
          <Avatar className="w-10 h-10">
            <AvatarFallback>{session!!.user.name?.[0].toUpperCase()}</AvatarFallback>
          </Avatar>
          <span>{session!!.user.name}</span>
          <Button onClick={logout}>Logout</Button>
        </div>
      </div>
    </>
  );
}

export default function layout({ children }: { children: React.ReactNode }) {
  const { data: session, status } = useSession()
  if (status === "loading") {
    return (<p>Please wait</p>);
  }

  if (session === null && status === "unauthenticated") {
    redirect("/");
  }

  return (
    <div className="container mx-auto p-4">
      <NavBar session={session!!} />
      {children}
    </div>
  )
}
