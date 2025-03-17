import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";
import { ReloadIcon, UpdateIcon } from "@radix-ui/react-icons";
import {
  CopyIcon,
  DollarSign,
  ShuffleIcon,
  UploadIcon,
  WalletIcon,
} from "lucide-react";
import React from "react";
import TopupForm from "./TopupForm";
import WithdrawalForm from "./WithdrawalForm";
import TransferForm from "./TransferForm";
import { Avatar, AvatarFallback } from "@/components/ui/avatar";

export default function Wallet() {
  return (
    <div className="flex flex-col items-center">
      <div className="pt-10 w-full lg:w-[60%]">
        <Card>
          <CardHeader className="pb-9">
            <div className="flex justify-between items-center">
              <div className="flex items-center gap-5">
                <WalletIcon size={30} />
                <div>
                  <CardTitle className="text-2xl">My Wallet</CardTitle>
                  <div className="flex items-center gap-2">
                    <p className="text-gray-200 text-s,">#A475Ed</p>
                    <CopyIcon
                      size={12}
                      className="cursor-pointer hover:text-slate-300"
                    />
                  </div>
                </div>
              </div>
              <div>
                <ReloadIcon className="w-6 h-6 cursor-pointer hover:text-gray-400" />
              </div>
            </div>
          </CardHeader>
          <CardContent>
            <div className="flex items-center ">
              <DollarSign />
              <span className="text-2xl font-semibold">20000</span>
            </div>
            <div className="flex gap-7 mt-5 ">
              <Dialog>
                <DialogTrigger>
                  <div
                    className="h-24 w-24 hover:text-gray-400 cursor-pointer flex
                   flex-col items-center justify-center rounded-md shadow-slate-800 shadow-md"
                  >
                    <UploadIcon />
                    <span className="text-sm mt-2">Add Money</span>
                  </div>
                </DialogTrigger>
                <DialogContent>
                  <DialogHeader>
                    <DialogTitle>Top Up Your Wallet</DialogTitle>
                  </DialogHeader>
                  <TopupForm />
                </DialogContent>
              </Dialog>

              <Dialog>
                <DialogTrigger>
                  <div
                    className="h-24 w-24 hover:text-gray-400 cursor-pointer flex
                   flex-col items-center justify-center rounded-md shadow-slate-800 shadow-md"
                  >
                    <UploadIcon />
                    <span className="text-sm mt-2">Withdrawal</span>
                  </div>
                </DialogTrigger>
                <DialogContent>
                  <DialogHeader>
                    <DialogTitle>Request Withdrawal</DialogTitle>
                  </DialogHeader>
                  <WithdrawalForm />
                </DialogContent>
              </Dialog>

              <Dialog>
                <DialogTrigger>
                  <div
                    className="h-24 w-24 hover:text-gray-400 cursor-pointer flex
                   flex-col items-center justify-center rounded-md shadow-slate-800 shadow-md"
                  >
                    <ShuffleIcon />
                    <span className="text-sm mt-2">Transfer</span>
                  </div>
                </DialogTrigger>
                <DialogContent>
                  <DialogHeader>
                    <DialogTitle className="text-center text-xl">
                      Transfer to other wallet
                    </DialogTitle>
                  </DialogHeader>
                  <TransferForm />
                </DialogContent>
              </Dialog>
            </div>
          </CardContent>
        </Card>
        <div className="py-5 pt-10">
          <div className="flex gap-2 items-center pb-5">
            <h1 className="text-2xl font-semibold">History</h1>
            <UpdateIcon className="p-0 h-7 w-7 cursor-pointer hover:text-gray-400" />
          </div>

          {/* <Separator /> */}
          <div className="space-y-5">
            {[1, 1, 1, 1, 1, 1, 1, 1]?.map((item, index) => (
              <div key={index}>
                <Card className="lg:w-[50] px-5 py-2 flex flex-row justify-between items-center">
                  <div className="flex items-center gap-5">
                    <Avatar>
                      <AvatarFallback>
                        <ShuffleIcon />
                      </AvatarFallback>
                    </Avatar>
                    <div className="space-y-1">
                      <h1>Buy Asset</h1>
                      <p className="text-sm text-gray-500">2024-06-04</p>
                    </div>
                  </div>
                  <div>
                    <p className="flex items-center">
                      {/* <DollarSign className="h-4 w-4" /> */}
                      <span
                        className={`${
                          20 > 0 ? "text-green-500" : "text-red-500"
                        }`}
                      >
                        20 USD
                      </span>
                    </p>
                  </div>
                </Card>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
