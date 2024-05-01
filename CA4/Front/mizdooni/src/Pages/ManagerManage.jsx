import React from "react";
import { Header } from "../components/Header";
import { ReserveCard } from "../components/ReserveCard";
import Footer from "../components/Footer";
import { ReserveTableItem } from "../components/ReserveTableItem";
import "../classes.css";
import { useLocation } from "react-router-dom";

export const ManagerManage = () => {

  return (
    <>
      <Header />
      <div className="h-100">
        <div className="container-fluid text-bg-danger bg-danger d-flex justify-content-between text-light">
          <div className="col-auto ms-5">Ali Daei Dizi</div>
          <div className="col-10 me-5 text-end px-5">
            Address: Vali-e-Asr Square, Boshehr, Iran
          </div>
        </div>

        <div className="container-fluid d-flex h-100 justify-content">
          <div className="col-4">
            <div className="container h-100 border-end border-5 border-danger">
              <div className="row align-items-center">
                <div className="col py-3 rounded-top">
                  <span className="font-weight-bold">Reservation List</span>
                </div>
                <div className="col text-end">
                  <span className="col me-1 gray fs-10">
                    Select a table to see its reservations
                  </span>
                </div>
              </div>
              <table className="table">
                <tbody>
                  <ReserveTableItem />
                  <ReserveTableItem />
                  <ReserveTableItem />
                  <ReserveTableItem />
                  <ReserveTableItem />
                  <ReserveTableItem />
                  <ReserveTableItem />
                </tbody>
              </table>
            </div>
          </div>

          <div className="col lightPink">
            <div className="container bg-light">
              <div className="row d-flex justify-content-start align-items-start">
                <a href="/" className="text-decoration-none text-danger">
                  +Add Table
                </a>
              </div>
              <div className="container align-self-center">
                <div className="row justify-content-center">
                  <ReserveCard />
                  <ReserveCard />
                  <ReserveCard />
                  <ReserveCard />
                  <ReserveCard />
                  <ReserveCard />
                  <ReserveCard />
                  <ReserveCard />
                  <ReserveCard />
                  <ReserveCard />
                  <ReserveCard />
                  <ReserveCard />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <Footer />
    </>
  );
};
