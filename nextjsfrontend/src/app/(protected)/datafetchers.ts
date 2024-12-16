"use server";
import { serverSession } from "@/auth";
import type { Result } from "@/app/types/dto";
import { Error, ResultType, Book } from "@/app/types/dto";


export { getBooks };

interface BooksResponse {content: Book[]}

async function getBooks(): Promise<Result<Book[]>> {
  const session = await serverSession();
  if (session === null) {
    return { type: ResultType.ERROR, error: Error.UN_AUTHORIZED }
  }

  const response = await fetch(`${process.env.BACKEND_URI}/api/books`, {
    headers: { "Authorization": `Bearer ${session.access_token}` }
  });

  if (!response.ok) {
    const t = await response.text();
    console.error("Backend Error: ", response.headers.get("Www-Authenticate"));
    console.error("Status: ", response.status);
    return { type: ResultType.ERROR, error: Error.BACKEND_ERROR, errorMsg: t };
  }

  try {
    const json: BooksResponse = await response.json();
    return {type: ResultType.Ok, data: json.content};
  } catch (e) {
    console.error(e);
    const t = await response.text();
    console.error("Response body: ", t);
    return { type: ResultType.ERROR, error: Error.JSON_PARSE_ERROR, errorMsg: `${e}` };
  }
}

