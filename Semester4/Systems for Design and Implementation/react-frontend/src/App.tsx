import * as React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { AppHome } from "./components/AppHome";
import { AppMenu } from "./components/AppMenu";
import { AllClients } from "./components/client/AllClients";
import { ClientDetails } from "./components/client/ClientDetail";
import { ClientDelete } from "./components/client/ClientDelete";
import { ClientAdd } from "./components/client/ClientAdd";
import { ClientUpdate } from "./components/client/ClientUpdate";
import { ProfitStatistic } from "./components/statistics/ProfitStatistic";
import { AllAttorneys } from "./components/attorney/AllAttorneys";
import { AttorneyDetails } from "./components/attorney/AttorneyDetail";
import { AttorneyUpdate } from "./components/attorney/AttorneyUpdate";
import { AttorneyDelete } from "./components/attorney/AttorneyDelete";
import { AttorneyAdd } from "./components/attorney/AttorneyAdd";
import { AllLawsuits } from "./components/lawsuit/AllLawsuits";
import { LawsuitDetails } from "./components/lawsuit/LawsuitDetail";
import { LawsuitUpdate } from "./components/lawsuit/LawsuitUpdate";
import { LawsuitDelete } from "./components/lawsuit/LawsuitDelete";
import { LawsuitAdd } from "./components/lawsuit/LawsuitAdd";
import { AllAOLs } from "./components/attorneyOnLawsuit/AllAOL";
import { AOLDetails } from "./components/attorneyOnLawsuit/AOLDetail";
import { AOLUpdate } from "./components/attorneyOnLawsuit/AOLUpdate";
import { AOLDelete } from "./components/attorneyOnLawsuit/AOLDelete";
import { AOLAdd } from "./components/attorneyOnLawsuit/AOLAdd";
import { AttorneyTravelStatistic } from "./components/statistics/AttorneyTravelStatistic";

function App() {
	return (
		<React.Fragment>
			<Router>
				<AppMenu />
				<Routes>
					<Route path="/" element={<AppHome />} />
					<Route path="/client" element={<AllClients />} />
					<Route path="/client/:clientId/details" element={<ClientDetails />} />
					<Route path="/client/:clientId/edit" element={<ClientUpdate />} />
					<Route path="/client/:clientId/delete" element={<ClientDelete />} />
					<Route path="/client/add" element={<ClientAdd />} />
					<Route path="/attorney" element={<AllAttorneys />} />
					<Route path="/attorney/:attorneyId/details" element={<AttorneyDetails />} />
					<Route path="/attorney/:attorneyId/edit" element={<AttorneyUpdate />} />
					<Route path="/attorney/:attorneyId/delete" element={<AttorneyDelete />} />
					<Route path="/attorney/add" element={<AttorneyAdd />} />
					<Route path="/lawsuit" element={<AllLawsuits />} />
					<Route path="/lawsuit/:lawsuitId/details" element={<LawsuitDetails />} />
					<Route path="/lawsuit/:lawsuitId/edit" element={<LawsuitUpdate />} />
					<Route path="/lawsuit/:lawsuitId/delete" element={<LawsuitDelete />} />
					<Route path="/lawsuit/add" element={<LawsuitAdd />} />
					<Route path="/attorney-on-lawsuit" element={<AllAOLs />} />
					<Route path="/attorney-on-lawsuit/:aolId/details" element={<AOLDetails />} />
					<Route path="/attorney-on-lawsuit/:aolId/edit" element={<AOLUpdate />} />
					<Route path="/attorney-on-lawsuit/:aolId/delete" element={<AOLDelete />} />
					<Route path="/attorney-on-lawsuit/add" element={<AOLAdd />} />
					<Route path="/top-profits" element={<ProfitStatistic/>} />
					<Route path="/attorney-travel" element={<AttorneyTravelStatistic/>} />
				</Routes>
			</Router>
		</React.Fragment>
	);
}

export default App;