/*eslint-disable*/
import React from "react";

// reactstrap components
import {Container} from "reactstrap";

function TransparentFooter() {
    return (
        <footer className="footer">
            <Container>
                <nav>
                    <ul>
                        <li>
                            <a
                                href="https://kristiania.no"
                                target="_blank"
                            >
                                VySmidig 2019
              </a>
                        </li>
                    </ul>
                </nav>
            </Container>
        </footer>
    );
}

export default TransparentFooter;
