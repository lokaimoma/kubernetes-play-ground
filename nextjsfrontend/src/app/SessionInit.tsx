"use client";
import { SessionProvider } from "next-auth/react";
import React from "react";

export default function SessionProviderInit({children}: {children: React.ReactNode}) {
  return (
    <SessionProvider>
    {children}
    </SessionProvider>
  )	
}
