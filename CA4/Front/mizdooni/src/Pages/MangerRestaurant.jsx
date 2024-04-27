import React from "react";
import { Header } from "../components/Header";
import Footer from "../components/Footer";
import "../classes.css";

export const MangerRestaurant = () => {
  return (
    <>
      <Header />
      <div class="col-6 mx-auto py-5">
        <div class="row mt-3">
          <table class="table">
            <div class="container">
              <div class="row bg-light rounded-top align-items-center">
                <div class="col py-3">My Restaurant</div>
                <div class="col text-end">
                  <button type="button" class="btn btn-danger rounded-5">
                    Add
                  </button>
                </div>
              </div>
            </div>

            <tbody>
              <tr class="align-items-center">
                <th scope="row" class="text-success align-middle">
                  Ali daei Dizi
                </th>
                <td class="text-danger align-middle">Boosher, Iran</td>
                <td class="text-end">
                  <a href="/" class="text-decoration-none text-danger">
                    <button type="button" class="btn btn-danger rounded-5">
                      Manage
                    </button>
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
