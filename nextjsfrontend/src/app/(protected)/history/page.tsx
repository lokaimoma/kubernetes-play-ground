'use client'

import { Button } from "@/components/ui/button"
import { Avatar, AvatarFallback } from "@/components/ui/avatar"
import { borrowRecords } from '@/app/mockData';
import { useSession } from "next-auth/react";

export default function BorrowHistory() {
  const { data: session, status } = useSession()

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
              <td className="py-2 px-4 border-b">{record.bookTitle}</td>
              <td className="py-2 px-4 border-b">{record.borrowDate}</td>
              <td className="py-2 px-4 border-b">{record.returnDate || 'Not returned'}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </>
  )
}


