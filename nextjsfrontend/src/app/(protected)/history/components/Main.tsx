'use client'

import { Button } from "@/components/ui/button"
import { Avatar, AvatarFallback } from "@/components/ui/avatar"
import { useSession } from "next-auth/react";
import { BookLoan } from "@/app/types/dto";

export function Main({borrowRecords}: {borrowRecords: BookLoan[]}) {

  return (
    <>
      <table className="min-w-full bg-white">
        <thead>
          <tr>
            <th className="py-2 px-4 border-b">Book Title</th>
            <th className="py-2 px-4 border-b">Borrow Date</th>
            <th className="py-2 px-4 border-b">Return Date</th>
          </tr>
        </thead>
        <tbody>
          {borrowRecords.map(record => (
            <tr key={record.id}>
              <td className="py-2 px-4 border-b">{record.book.title}</td>
              <td className="py-2 px-4 border-b">{new Date(record.checkoutDate).toLocaleString()}</td>
              <td className="py-2 px-4 border-b">{record.returnDate ? new Date(record.returnDate).toLocaleString() : 'Not returned'}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </>
  )
}
