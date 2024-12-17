import { getBorrowHistory } from "@/app/(protected)/datafetchers";
import { ResultType, Error } from "@/app/types/dto";
import { redirect } from "next/navigation";
import { Main } from "./components/Main";

export default async function BorrowHistory() {
  const borrowRecords = await getBorrowHistory();
  if (borrowRecords.type === ResultType.ERROR || borrowRecords.data === undefined) {
    switch (borrowRecords.error) {
      case Error.JSON_PARSE_ERROR:
      case Error.BACKEND_ERROR:
      default:
        console.log("Error: ", borrowRecords.error, borrowRecords.errorMsg)
        redirect("/?error=BackendError");
    }
  }

  return (
    <>
      <Main borrowRecords={borrowRecords.data}/>
    </>
  )
}


