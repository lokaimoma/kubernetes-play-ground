'use client'

import { useState } from 'react'
import { Input } from "@/components/ui/input"
import { Button } from "@/components/ui/button"
import { Avatar, AvatarFallback } from "@/components/ui/avatar"
import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
  DialogFooter,
} from "@/components/ui/dialog"
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"
import { borrowRecords, BorrowRecord } from '@/app/mockData';
import { useSession } from 'next-auth/react'

export default function AdminPage() {
  const [searchTerm, setSearchTerm] = useState('')
  const [selectedRecord, setSelectedRecord] = useState<BorrowRecord | null>(null)
  const { data: session, status } = useSession()

  const filteredRecords = borrowRecords.filter(record =>
    record.bookTitle.toLowerCase().includes(searchTerm.toLowerCase()) ||
    record.borrower.toLowerCase().includes(searchTerm.toLowerCase())
  )

  const handleReturn = (record: BorrowRecord) => {
    // Here you would typically make an API call to update the book's status
    console.log(`Marking book as returned: ${record.bookTitle}`)
    // For now, we'll just close the dialog
    setSelectedRecord(null)
  }


  return (
    <>
      <Input
        type="search"
        placeholder="Search books or borrowers..."
        value={searchTerm}
        onChange={(e) => setSearchTerm(e.target.value)}
        className="mb-4"
      />
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>Book Title</TableHead>
            <TableHead>Borrower</TableHead>
            <TableHead>Borrow Date</TableHead>
            <TableHead>Action</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          {filteredRecords.map(record => (
            <TableRow key={record.id}>
              <TableCell>{record.bookTitle}</TableCell>
              <TableCell>{record.borrower}</TableCell>
              <TableCell>{record.borrowDate}</TableCell>
              <TableCell>
                <Dialog>
                  <DialogTrigger asChild>
                    <Button 
                      variant="outline" 
                      onClick={() => setSelectedRecord(record)}
                    >
                      Mark as Returned
                    </Button>
                  </DialogTrigger>
                  <DialogContent>
                    <DialogHeader>
                      <DialogTitle>Confirm Return</DialogTitle>
                      <DialogDescription>
                        Are you sure you want to mark this book as returned?
                      </DialogDescription>
                    </DialogHeader>
                    <div>
                      <p><strong>Book:</strong> {selectedRecord?.bookTitle}</p>
                      <p><strong>Borrower:</strong> {selectedRecord?.borrower}</p>
                      <p><strong>Borrow Date:</strong> {selectedRecord?.borrowDate}</p>
                    </div>
                    <DialogFooter>
                      <Button variant="outline" onClick={() => setSelectedRecord(null)}>Cancel</Button>
                      <Button onClick={() => selectedRecord && handleReturn(selectedRecord)}>Confirm Return</Button>
                    </DialogFooter>
                  </DialogContent>
                </Dialog>
              </TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </>
  )
}


