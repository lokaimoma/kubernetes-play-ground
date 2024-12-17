"use server";
import { serverSession } from "@/auth";
import type { BookLoan, Result } from "@/app/types/dto";
import { Error, ResultType, Book } from "@/app/types/dto";
import { redirect, RedirectType } from "next/navigation";


export { getBooks, getBorrowHistory };

async function getBooks(): Promise<Result<Book[]>> {
  return fetchPagedData<Book>("/api/books");
}

async function getBorrowHistory(): Promise<Result<BookLoan[]>> {
  const session = await serverSession();
  if (session === null) {
    // return { type: ResultType.ERROR, error: Error.UN_AUTHORIZED }
    return redirect("/?error=tokenError", RedirectType.replace);
  }
  return fetchPagedData<BookLoan>(`/umgmt/loan?email=${session.user.email}`);
}

interface ApiResponse<T> { content: T[] }

async function fetchPagedData<T>(dataEndpoint: string): Promise<Result<T[]>> {
  const session = await serverSession();
  if (session === null) {
    // return { type: ResultType.ERROR, error: Error.UN_AUTHORIZED }
    return redirect("/?error=tokenError", RedirectType.replace);
  }

  let response;

  try {
    response = await fetch(`${process.env.BACKEND_URI}${dataEndpoint}`, {
      headers: { "Authorization": `Bearer ${session.access_token}` }
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

  try {
    const json: ApiResponse<T> = await response.json();
    return { type: ResultType.Ok, data: json.content ?? json };
  } catch (e) {
    console.error(e);
    const t = await response.text();
    console.error("Response body: ", t);
    return { type: ResultType.ERROR, error: Error.JSON_PARSE_ERROR, errorMsg: `${e}` };
  }
}

