import React from "react";
import "../classes.css";
import "bootstrap/dist/css/bootstrap.min.css";
import Footer from "../components/Footer";
import { Header } from "../components/Header";

export const Customer = () => {
  return (
    <>
      <Header />
      <div className="container py-5">
        <div className="row">
          <div className="col">
            <span>Your reservations are also emailed to </span>
            <a href="/" className="text-decoration-none">
              <span className="text-danger">Tom_holland@ut.ac.ir</span>
            </a>
          </div>

          <div className="col text-end">Address: Tehran, Iran</div>
        </div>

        <div className="row mt-3">
          <table className="table">
            <div className="py-3 rounded-top">My Reservations</div>
            <tbody>
              <tr>
                <th scope="row" className="text-success">
                  2024-06-22 16:00
                </th>
                <td className="text-danger">ali daei dizi</td>
                <td className="text-success">Table-12</td>
                <td className="text-success">4-seats</td>
                <td className="text-center">
                  <a href="/" className="text-decoration-none text-danger">
                    Cancel
                  </a>
                </td>
              </tr>
              <tr>
                <th scope="row" className="text-muted">
                  2024-06-22 16:00
                </th>
                <td className="text-danger">ali daei dizi</td>
                <td className="text-muted">Table-12</td>
                <td className="text-muted">4-seats</td>
                <td className="text-center">
                  <a href="/" className="text-decoration-none text-danger">
                    Add Comment
                  </a>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
      <Footer />
    </>
  );
};
