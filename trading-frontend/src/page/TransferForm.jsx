import { Button } from "@/components/ui/button";
import { DialogClose } from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import React, { useState } from "react";

export default function TransferForm() {
  const [formData, setFormData] = useState({
    amount: "",
    walletId: "",
    purpose: "",
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = () => {
    if (!formData.amount || !formData.walletId || !formData.purpose) {
      alert("Please fill in all fields before submitting.");
      return;
    }
    console.log("Form Data:", formData);
  };

  return (
    <div className="pt-10 space-y-5">
      <div>
        <h1 className="pb-1">Enter Amount</h1>
        <Input
          type="number"
          name="amount"
          onChange={handleChange}
          value={formData.amount}
          className="py-7"
          placeholder="$99999"
        />
      </div>

      <div>
        <h1 className="pb-1">Wallet ID</h1>
        <Input
          name="walletId"
          onChange={handleChange}
          value={formData.walletId}
          className="py-7"
          placeholder="#ADER455"
        />
      </div>

      <div>
        <h1 className="pb-1">Enter Purpose</h1>
        <Input
          name="purpose"
          onChange={handleChange}
          value={formData.purpose}
          className="py-7"
          placeholder="Gift for your friend..."
        />
      </div>

      <DialogClose asChild>
        <div>
          <Button onClick={handleSubmit} className="w-full py-7">
            Submit
          </Button>
        </div>
      </DialogClose>
    </div>
  );
}
