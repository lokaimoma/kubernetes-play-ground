"use server";

import { serverSession } from "@/auth";
import { redirect, RedirectType } from "next/navigation";
import { ResultType, Error, Result } from "@/app/types/dto";

export { loanBook, returnBook };

async function returnBook(bookId: string, userEmail: string) {
  return action("/umgmt/loan/return", JSON.stringify({bookId, userEmail}));
}

async function loanBook(bookId: string) {
  const session = await serverSession();
  if (session === null) {
    return redirect("/?error=tokenError", RedirectType.replace);
  }
  return action("/umgmt/loan", JSON.stringify({bookId, userEmail: session.user.email}))
}

async function action(endpoint: string, json: string): Promise<Result<string>>{
  const session = await serverSession();
  if (session === null) {
    return redirect("/?error=tokenError", RedirectType.replace);
  }
  

  let response;

  try {
    response = await fetch(`${process.env.BACKEND_URI}${endpoint}`, {
      headers: { "Authorization": `Bearer ${session.access_token}`, "Content-Type": "application/json" },
      method: "POST",
      body: json
    });
  } catch (e) {
    console.log("Fetch error: ", e);
    return { type: ResultType.ERROR, error: Error.UN_KNOWN };
  }

  
  if (!response.ok) {
    const t = await response.text();
    console.error("Backend Error: ", response.headers.get("Www-Authenticate"));
    console.error("Status: ", response.status);
    return { type: ResultType.ERROR, error: Error.BACKEND_ERROR, errorMsg: t };
  }

  const t = await response.text();

  return {type: ResultType.Ok, data: t};
}
