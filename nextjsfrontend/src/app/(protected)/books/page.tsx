import {getBooks} from "@/app/(protected)/datafetchers";
import {ResultType, Error} from "@/app/types/dto";
import { redirect } from "next/navigation";
import { Main } from "./components/main";

export default async function Home() {
  const books = await getBooks();
  if (books.type === ResultType.ERROR || books.data === undefined) {
    switch (books.error) {
      case Error.JSON_PARSE_ERROR:
      case Error.BACKEND_ERROR:
      default:
        redirect("/?error=BackendError");
    }
  }


  return (
    <Main books={books.data} />
  )
}

