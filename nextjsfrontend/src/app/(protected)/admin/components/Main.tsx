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
import { BookloandAdminRecord, ResultType } from '@/app/types/dto'
import { returnBook } from "@/app/(protected)/actions";
import { Loader2 } from 'lucide-react'
import { useRouter } from 'next/navigation'

export function Main({ borrowRecords }: { borrowRecords: BookloandAdminRecord[] }) {
  const router = useRouter();
  const [searchTerm, setSearchTerm] = useState('')
  const [dialogOpen, setDialogOpen] = useState(false);
  const [isProcessing, setIsProcessing] = useState(false);

  const filteredRecords = borrowRecords.filter(record =>
    record.book.title.toLowerCase().includes(searchTerm.toLowerCase()) ||
    record.userEmail.toLowerCase().includes(searchTerm.toLowerCase())
  )

  const handleReturn = (record: BookloandAdminRecord) => {
    setIsProcessing(true);
    returnBook(record.book.id, record.userEmail).then(res => {
      if (res.type === ResultType.ERROR) {
        alert(res.errorMsg ?? "An error ooccured. Try again later");
      } else {
        alert(res.data ?? "Status saved");
        router.refresh();
      }
    }).finally(() => {
      setDialogOpen(false);
      setIsProcessing(false);
    })
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
              <TableCell>{record.book.title}</TableCell>
              <TableCell>{record.userEmail}</TableCell>
              <TableCell>{record.checkoutDate}</TableCell>
              <TableCell>
                <Dialog open={dialogOpen} onOpenChange={setDialogOpen}>
                  <DialogTrigger asChild>
                    <Button
                      variant="outline"
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
                      <p><strong>Book:</strong> {record.book.title}</p>
                      <p><strong>Borrower:</strong> {record.userEmail}</p>
                      <p><strong>Borrow Date:</strong> {record.checkoutDate}</p>
                    </div>
                    <DialogFooter>
                      <Button variant="outline" onClick={() => setDialogOpen(false)} disabled={isProcessing}>Cancel</Button>
                      {isProcessing ? (<>
                        <Button>
                          <Loader2 className='animate-spin' />
                          Please wait...
                        </Button>
                      </>) : (<Button onClick={() => handleReturn(record)}>Confirm Return</Button>)}
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
