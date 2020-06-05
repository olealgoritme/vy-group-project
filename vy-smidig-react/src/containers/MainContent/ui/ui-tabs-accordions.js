import React, { Component } from 'react';
import { TabContent, TabPane, Nav, NavItem, NavLink, Card, CardText, Row, Col, Collapse, CardBody } from 'reactstrap';
import classnames from 'classnames';
import { Link } from 'react-router-dom';
import { activateAuthLayout } from '../../../store/actions';
import { connect } from 'react-redux';
import Settingmenu from '../Subpages/Settingmenu';

class Uitabsaccordions extends Component {

    constructor(props) {
        super(props);

        this.state = {
            activeTab: '1',
            activeTab1: '5',
            activeTab2: '9',
            activeTab3: '13',
            col1: true,
            col2: false,
            col3: false,
            col5: true,
        };


        this.toggle = this.toggle.bind(this);
        this.toggle1 = this.toggle1.bind(this);

        this.t_col1 = this.t_col1.bind(this);
        this.t_col2 = this.t_col2.bind(this);
        this.t_col3 = this.t_col3.bind(this);
        this.t_col5 = this.t_col5.bind(this);

        this.toggle2 = this.toggle2.bind(this);
        this.toggle3 = this.toggle3.bind(this);

    }

    componentDidMount() {
        this.props.activateAuthLayout();
    }


    t_col1() {
        this.setState({ col1: !this.state.col1 });
    }
    t_col2() {
        this.setState({ col2: !this.state.col2 });
    }
    t_col3() {
        this.setState({ col3: !this.state.col3 });
    }
    t_col5() {
        this.setState({ col5: !this.state.col5 });
    }

    toggle(tab) {
        if (this.state.activeTab !== tab) {
            this.setState({
                activeTab: tab
            });
        }
    }
    toggle1(tab) {
        if (this.state.activeTab1 !== tab) {
            this.setState({
                activeTab1: tab
            });
        }
    }
    toggle2(tab) {
        if (this.state.activeTab2 !== tab) {
            this.setState({
                activeTab2: tab
            });
        }
    }
    toggle3(tab) {
        if (this.state.activeTab3 !== tab) {
            this.setState({
                activeTab3: tab
            });
        }
    }

    render() {

        return (
            <React.Fragment>

                <div className="content">
                    <div className="container-fluid">
                        <div className="page-title-box">
                            <Row className="align-items-center">
                                <Col sm="6">
                                    <h4 className="page-title">Tabs & Accordions</h4>
                                    <ol className="breadcrumb">
                                        <li className="breadcrumb-item"><Link to="#"><i className="mdi mdi-home-outline"></i></Link></li>
                                        <li className="breadcrumb-item"><Link to="#">UI Elements</Link></li>
                                        <li className="breadcrumb-item active">Tabs & Accordions</li>
                                    </ol>
                                </Col>
                                <Col sm="6">
                                    <div className="float-right d-none d-md-block">
                                        <Settingmenu />
                                    </div>
                                </Col>
                            </Row>
                        </div>

                        <Row>
                            <Col xl="6">
                                <Card>
                                    <CardBody>

                                        <h4 className="mt-0 header-title">Default Tabs</h4>
                                        <p className="text-muted mb-4">Use the tab JavaScript plugin—include
                                            it individually or through the compiled <code className="highlighter-rouge">bootstrap.js</code>
                                            file—to extend our navigational tabs and pills to create tabbable panes
                                            of local content, even via dropdown menus.</p>

                                        <Nav tabs>
                                            <NavItem>
                                                <NavLink
                                                    className={classnames({ active: this.state.activeTab === '1' })}
                                                    onClick={() => { this.toggle('1'); }}
                                                >
                                                    Home
                                        </NavLink>
                                            </NavItem>
                                            <NavItem>
                                                <NavLink
                                                    className={classnames({ active: this.state.activeTab === '2' })}
                                                    onClick={() => { this.toggle('2'); }}
                                                >
                                                    Profile
                                        </NavLink>
                                            </NavItem>
                                            <NavItem>
                                                <NavLink
                                                    className={classnames({ active: this.state.activeTab === '3' })}
                                                    onClick={() => { this.toggle('3'); }}
                                                >
                                                    Messages
                                        </NavLink>
                                            </NavItem>
                                            <NavItem>
                                                <NavLink
                                                    className={classnames({ active: this.state.activeTab === '4' })}
                                                    onClick={() => { this.toggle('4'); }}
                                                >
                                                    Settings
                                        </NavLink>
                                            </NavItem>
                                        </Nav>

                                        <TabContent activeTab={this.state.activeTab}>
                                            <TabPane tabId="1" className="p-3">
                                                <Row>
                                                    <Col sm="12">
                                                        <CardText>
                                                            Raw denim you probably haven't heard of them jean shorts Austin.
                                                Nesciunt tofu stumptown aliqua, retro synth master cleanse. Mustache
                                                cliche tempor, williamsburg carles vegan helvetica. Reprehenderit
                                                butcher retro keffiyeh dreamcatcher synth. Cosby sweater eu banh mi,
                                                qui irure terry richardson ex squid. Aliquip placeat salvia cillum
                                                iphone. Seitan aliquip quis cardigan american apparel, butcher
                                                 voluptate nisi qui.
                                                    </CardText>
                                                    </Col>
                                                </Row>
                                            </TabPane>
                                            <TabPane tabId="2" className="p-3">
                                                <Row>
                                                    <Col sm="12">
                                                        <CardText>
                                                            Food truck fixie locavore, accusamus mcsweeney's marfa nulla
                                                    single-origin coffee squid. Exercitation +1 labore velit, blog
                                                    sartorial PBR leggings next level wes anderson artisan four loko
                                                    farm-to-table craft beer twee. Qui photo booth letterpress,
                                                    commodo enim craft beer mlkshk aliquip jean shorts ullamco ad
                                                    vinyl cillum PBR. Homo nostrud organic, assumenda labore
                                                    aesthetic magna delectus mollit. Keytar helvetica VHS salvia yr,
                                                    vero magna velit sapiente labore stumptown. Vegan fanny pack
                                                odio cillum wes anderson 8-bit.
                                                    </CardText>
                                                    </Col>
                                                </Row>
                                            </TabPane>
                                            <TabPane tabId="3" className="p-3">
                                                <Row>
                                                    <Col sm="12">
                                                        <CardText>
                                                            Etsy mixtape wayfarers, ethical wes anderson tofu before they
                                                           sold out mcsweeney's organic lomo retro fanny pack lo-fi
                                                           farm-to-table readymade. Messenger bag gentrify pitchfork
                                                           tattooed craft beer, iphone skateboard locavore carles etsy
                                                           salvia banksy hoodie helvetica. DIY synth PBR banksy irony.
                                                           Leggings gentrify squid 8-bit cred pitchfork. Williamsburg banh
                                                           mi whatever gluten-free, carles pitchfork biodiesel fixie etsy
                                                           retro mlkshk vice blog. Scenester cred you probably haven't
                                                           heard of them, vinyl craft beer blog stumptown. Pitchfork
                                                       sustainable tofu synth chambray yr.
                                                    </CardText>
                                                    </Col>
                                                </Row>
                                            </TabPane>
                                            <TabPane tabId="4" className="p-3">
                                                <Row>
                                                    <Col sm="12">
                                                        <CardText>
                                                            Trust fund seitan letterpress, keytar raw denim keffiyeh etsy
                                                        art party before they sold out master cleanse gluten-free squid
                                                        scenester freegan cosby sweater. Fanny pack portland seitan DIY,
                                                        art party locavore wolf cliche high life echo park Austin. Cred
                                                        vinyl keffiyeh DIY salvia PBR, banh mi before they sold out
                                                        farm-to-table VHS viral locavore cosby sweater. Lomo wolf viral,
                                                        mustache readymade thundercats keffiyeh craft beer marfa
                                                        ethical. Wolf salvia freegan, sartorial keffiyeh echo park
                                                    vegan.
                                                    </CardText>
                                                    </Col>
                                                </Row>
                                            </TabPane>
                                        </TabContent>

                                    </CardBody>
                                </Card>
                            </Col>

                            <Col xl="6">
                                <Card>
                                    <CardBody>

                                        <h4 className="mt-0 header-title">Justify Tabs</h4>
                                        <p className="text-muted mb-4">Use the tab JavaScript plugin—include
                                            it individually or through the compiled <code className="highlighter-rouge">bootstrap.js</code>
                                            file—to extend our navigational tabs and pills to create tabbable panes
                                            of local content, even via dropdown menus.</p>

                                        <Nav pills className="navtab-bg nav-justified">
                                            <NavItem>
                                                <NavLink
                                                    className={classnames({ active: this.state.activeTab1 === '5' })}
                                                    onClick={() => { this.toggle1('5'); }}
                                                >
                                                    Home
                                        </NavLink>
                                            </NavItem>
                                            <NavItem>
                                                <NavLink
                                                    className={classnames({ active: this.state.activeTab1 === '6' })}
                                                    onClick={() => { this.toggle1('6'); }}
                                                >
                                                    Profile
                                        </NavLink>
                                            </NavItem>
                                            <NavItem>
                                                <NavLink
                                                    className={classnames({ active: this.state.activeTab1 === '7' })}
                                                    onClick={() => { this.toggle1('7'); }}
                                                >
                                                    Messages
                                        </NavLink>
                                            </NavItem>
                                            <NavItem>
                                                <NavLink
                                                    className={classnames({ active: this.state.activeTab1 === '8' })}
                                                    onClick={() => { this.toggle1('8'); }}
                                                >
                                                    Settings
                                        </NavLink>
                                            </NavItem>
                                        </Nav>

                                        <TabContent activeTab={this.state.activeTab1}>
                                            <TabPane tabId="5" className="p-3">
                                                <Row>
                                                    <Col sm="12">
                                                        <CardText>
                                                            Raw denim you probably haven't heard of them jean shorts Austin.
                                               Nesciunt tofu stumptown aliqua, retro synth master cleanse. Mustache
                                               cliche tempor, williamsburg carles vegan helvetica. Reprehenderit
                                               butcher retro keffiyeh dreamcatcher synth. Cosby sweater eu banh mi,
                                               qui irure terry richardson ex squid. Aliquip placeat salvia cillum
                                               iphone. Seitan aliquip quis cardigan american apparel, butcher
                                                voluptate nisi qui.
                                                    </CardText>
                                                    </Col>
                                                </Row>
                                            </TabPane>
                                            <TabPane tabId="6" className="p-3">
                                                <Row>
                                                    <Col sm="12">
                                                        <CardText>
                                                            Food truck fixie locavore, accusamus mcsweeney's marfa nulla
                                                      single-origin coffee squid. Exercitation +1 labore velit, blog
                                                      sartorial PBR leggings next level wes anderson artisan four loko
                                                      farm-to-table craft beer twee. Qui photo booth letterpress,
                                                      commodo enim craft beer mlkshk aliquip jean shorts ullamco ad
                                                      vinyl cillum PBR. Homo nostrud organic, assumenda labore
                                                      aesthetic magna delectus mollit. Keytar helvetica VHS salvia yr,
                                                      vero magna velit sapiente labore stumptown. Vegan fanny pack
                                                  odio cillum wes anderson 8-bit.
                                                    </CardText>
                                                    </Col>
                                                </Row>
                                            </TabPane>
                                            <TabPane tabId="7" className="p-3">
                                                <Row>
                                                    <Col sm="12">
                                                        <CardText>
                                                            Etsy mixtape wayfarers, ethical wes anderson tofu before they
                                                    sold out mcsweeney's organic lomo retro fanny pack lo-fi
                                                    farm-to-table readymade. Messenger bag gentrify pitchfork
                                                    tattooed craft beer, iphone skateboard locavore carles etsy
                                                    salvia banksy hoodie helvetica. DIY synth PBR banksy irony.
                                                    Leggings gentrify squid 8-bit cred pitchfork. Williamsburg banh
                                                    mi whatever gluten-free, carles pitchfork biodiesel fixie etsy
                                                    retro mlkshk vice blog. Scenester cred you probably haven't
                                                    heard of them, vinyl craft beer blog stumptown. Pitchfork
                                                sustainable tofu synth chambray yr.
                                                    </CardText>
                                                    </Col>
                                                </Row>
                                            </TabPane>

                                            <TabPane tabId="8" className="p-3">
                                                <Row>
                                                    <Col sm="12">
                                                        <CardText>
                                                            Trust fund seitan letterpress, keytar raw denim keffiyeh etsy
                                                  art party before they sold out master cleanse gluten-free squid
                                                  scenester freegan cosby sweater. Fanny pack portland seitan DIY,
                                                  art party locavore wolf cliche high life echo park Austin. Cred
                                                  vinyl keffiyeh DIY salvia PBR, banh mi before they sold out
                                                  farm-to-table VHS viral locavore cosby sweater. Lomo wolf viral,
                                                  mustache readymade thundercats keffiyeh craft beer marfa
                                                  ethical. Wolf salvia freegan, sartorial keffiyeh echo park
                                              vegan.
                                                    </CardText>
                                                    </Col>
                                                </Row>
                                            </TabPane>
                                        </TabContent>

                                    </CardBody>
                                </Card>
                            </Col>
                        </Row>


                        <Row>
                            <Col xl="6">
                                <Card>
                                    <CardBody>

                                        <h4 className="mt-0 header-title">Default Collapse</h4>
                                        <p className="text-muted mb-4">
                                            You can use a link with the <code className="highlighter-rouge">href</code>
                                            attribute, or a button with the <code className="highlighter-rouge">data-target</code>
                                            attribute. In both cases, the <code className="highlighter-rouge">data-toggle="collapse"</code>
                                            is required.
                                        </p>

                                        <p>
                                            <Link to="#" onClick={this.t_col5} className="btn btn-primary mo-mb-2">
                                                Link with href
                                        </Link> &nbsp;
                                        <button onClick={this.t_col5} className="btn btn-primary mo-mb-2" type="button">
                                                Button with data-target
                                    </button>
                                        </p>

                                        <Collapse isOpen={this.state.col5}>
                                            <Card>
                                                <CardBody>
                                                    Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident.
                                        </CardBody>
                                            </Card>
                                        </Collapse>
                                    </CardBody>
                                </Card>
                            </Col>

                            <Col xl="6">
                                <Card>
                                    <CardBody>

                                        <h4 className="mt-0 header-title">Accordion Example</h4>
                                        <p className="text-muted mb-4">Extend the default collapse behavior to create an accordion.</p>


                                        <div id="accordion">
                                            <div className="card mb-1">
                                                <div className="card-header p-3" id="headingOne">
                                                    <h6 className="m-0 font-14">
                                                        <span onClick={this.t_col1} className="text-dark">
                                                            Collapsible Group Item #1
                                                   </span>
                                                    </h6>
                                                </div>

                                                <Collapse isOpen={this.state.col1}>
                                                    <Card>
                                                        <CardBody>
                                                            Anim pariatur cliche reprehenderit, enim eiusmod high life
                                                            accusamus terry richardson ad squid. 3 wolf moon officia
                                                            aute, non cupidatat skateboard dolor brunch. Food truck
                                                            quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor,
                                                            sunt aliqua put a bird on it squid single-origin coffee
                                                            nulla assumenda shoreditch et. Nihil anim keffiyeh
                                                            helvetica, craft beer labore wes anderson cred nesciunt
                                                            sapiente ea proident. Ad vegan excepteur butcher vice lomo.
                                                            Leggings occaecat craft beer farm-to-table, raw denim
                                                            aesthetic synth nesciunt you probably haven't heard of them
                                                            accusamus labore sustainable VHS.
                                            </CardBody>
                                                    </Card>
                                                </Collapse>
                                            </div>
                                            <Card className="mb-1">
                                                <div className="card-header p-3" id="headingTwo">
                                                    <h6 className="m-0 font-14">
                                                        <span onClick={this.t_col2} className="text-dark" > Collapsible Group Item #2 </span>

                                                    </h6>
                                                </div>
                                                <Collapse isOpen={this.state.col2}>
                                                    <Card>
                                                        <CardBody>
                                                            Anim pariatur cliche reprehenderit, enim eiusmod high life
                                                            accusamus terry richardson ad squid. 3 wolf moon officia
                                                            aute, non cupidatat skateboard dolor brunch. Food truck
                                                            quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor,
                                                            sunt aliqua put a bird on it squid single-origin coffee
                                                            nulla assumenda shoreditch et. Nihil anim keffiyeh
                                                            helvetica, craft beer labore wes anderson cred nesciunt
                                                            sapiente ea proident. Ad vegan excepteur butcher vice lomo.
                                                            Leggings occaecat craft beer farm-to-table, raw denim
                                                            aesthetic synth nesciunt you probably haven't heard of them
                                                            accusamus labore sustainable VHS.
                                            </CardBody>
                                                    </Card>
                                                </Collapse>
                                            </Card>
                                            <Card className="mb-0">
                                                <div className="card-header p-3" id="headingThree">
                                                    <h6 className="m-0 font-14">
                                                        <span onClick={this.t_col3} className="text-dark">
                                                            Collapsible Group Item #3
                                                </span>
                                                    </h6>
                                                </div>
                                                <Collapse isOpen={this.state.col3}>
                                                    <Card>
                                                        <CardBody>
                                                            Anim pariatur cliche reprehenderit, enim eiusmod high life
                                                            accusamus terry richardson ad squid. 3 wolf moon officia
                                                            aute, non cupidatat skateboard dolor brunch. Food truck
                                                            quinoa nesciunt laborum eiusmod. Brunch 3 wolf moon tempor,
                                                            sunt aliqua put a bird on it squid single-origin coffee
                                                            nulla assumenda shoreditch et. Nihil anim keffiyeh
                                                            helvetica, craft beer labore wes anderson cred nesciunt
                                                            sapiente ea proident. Ad vegan excepteur butcher vice lomo.
                                                            Leggings occaecat craft beer farm-to-table, raw denim
                                                            aesthetic synth nesciunt you probably haven't heard of them
                                                            accusamus labore sustainable VHS.
                                            </CardBody>
                                                    </Card>
                                                </Collapse>
                                            </Card>
                                        </div>
                                    </CardBody>
                                </Card>
                            </Col>
                        </Row>
                    </div>
                </div>
            </React.Fragment>
        );
    }
}


export default connect(null, { activateAuthLayout })(Uitabsaccordions);


