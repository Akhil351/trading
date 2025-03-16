import { Button } from "@/components/ui/button";
import React, { useState } from "react";
import ReactApexChart from "react-apexcharts";

export default function StockChart() {
  const [activeLable, setActiveLable] = useState("1 Day");
  const handleActiveLable = (value) => {
    setActiveLable(value);
  };
  const timeSeries = [
    {
      keyword: "DIGITAL_CURRENCY_DAILY",
      key: "Time Series (Daily)",
      lable: "1 Day",
      value: 1,
    },
    {
      keyword: "DIGITAL_CURRENCY_WEEKLY",
      key: "Weekly Time Series",
      lable: "1 Week",
      value: 7,
    },
    {
      keyword: "DIGITAL_CURRENCY_MONTHLY",
      key: "Monthly Time Series",
      lable: "1 Month",
      value: 30,
    },
    {
      keyword: "DIGITAL_CURRENCY_MONTHLY_3",
      key: "3 Month Time Series",
      lable: "3 Month",
      value: 90,
    },
    {
      keyword: "DIGITAL_CURRENCY_MONTHLY_6",
      key: "6 Month Time Series",
      lable: "6 Month",
      value: 180,
    },
    {
      keyword: "DIGITAL_CURRENCY_YEARLY",
      key: "Yearly Time Series",
      lable: "1 year",
      value: 365,
    },
  ];
  const series = [
    {
      data: [
        [1739541856536, 96817.72457029505],
        [1739545448030, 97324.06933798644],
        [1739549040368, 96961.0929667185],
        [1739552654298, 97772.85353304356],
        [1739556258324, 98623.62137807804],
        [1739559815581, 98214.29854725617],
        [1739563467377, 97961.4270398272],
        [1739567331836, 97314.71761687646],
        [1739570661801, 97381.31607548901],
        [1739574249783, 97190.84281503958],
        [1739577847449, 97525.56432832252],
        [1739581364761, 97465.74346570221],
        [1739585061757, 97811.65069075664],
        [1739588470495, 97810.11853984169],
        [1739592231511, 97680.37583137362],
        [1739596141276, 97555.50423518383],
        [1739599451294, 97596.36340969727],
        [1739603364932, 97534.03412413939],
        [1739606638198, 97401.87016895857],
      ],
    },
  ];

  const options = {
    chart: {
      id: "area-datetime",
      type: "area",
      height: 450,
      zoom: {
        autoScaleYaxis: true,
      },
    },
    dataLabels: {
      enabled: false,
    },
    xaxis: {
      type: "datetime",
      tickAmount: 6,
    },
    colors: ["#758AA2"],
    markers: {
      colors: ["#fff"],
      strokeColor: "#fff",
      size: 0,
      strokeWidth: 1,
      style: "hollow",
    },
    tooltip: {
      theme: "dark",
    },
    fill: {
      type: "gradient",
      gradient: {
        shadeIntensity: 1,
        opacityFrom: 0.7,
        opacityTo: 0.9,
        stops: [0, 100],
      },
    },
    grid: {
      borderColor: "#4753SE",
      strokeDashArray: 4,
      show: true,
    },
  };

  return (
    <div>
      <div className="space-x-3">
        {timeSeries.map((item) => (
          <Button
            variant={activeLable==item.lable?"default":"outline"}
            onClick={() => handleActiveLable(item.lable)}
            key={item.lable}
          >
            {item.lable}
          </Button>
        ))}
      </div>
      <div id="chart-timelines">
        <ReactApexChart
          options={options}
          series={series}
          height={450}
          type="area"
        />
      </div>
    </div>
  );
}
