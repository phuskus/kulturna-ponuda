// This file is required by karma.conf.js and loads recursively all the .spec and framework files

import 'zone.js/dist/zone-testing';
import { getTestBed } from '@angular/core/testing';
import {
  BrowserDynamicTestingModule,
  platformBrowserDynamicTesting
} from '@angular/platform-browser-dynamic/testing';

declare const require: {
  context(path: string, deep?: boolean, filter?: RegExp): {
    keys(): string[];
    <T>(id: string): T;
  };
};

// First, initialize the Angular testing environment.
getTestBed().initTestEnvironment(
  BrowserDynamicTestingModule,
  platformBrowserDynamicTesting()
);
// Then we find all the tests.

// all tests!
const context = require.context('./', true, /\.spec\.ts$/);



// register
//const context = require.context('./', true, /app.modules.register.pages.register-page.register-page.component\.spec\.ts$/);

// category card
//const context = require.context('./', true, /app.modules.main.components.category-card.category-card.component\.spec\.ts$/);

// map
// const context = require.context('./', true, /app.modules.main.components.map.map.component\.spec\.ts$/);

// single offer
// const context = require.context('./', true, /app.modules.main.components.single-offer.single-offer.component\.spec\.ts$/);

// single post
// const context = require.context('./', true, /app.modules.main.components.single-post.single-post.component\.spec\.ts$/);

// single review
// const context = require.context('./', true, /app.modules.main.components.single-review.single-review.component\.spec\.ts$/);

// cardbar
//const context = require.context('./', true, /app.modules.main.components.cardbar.cardbar.component\.spec\.ts$/);

// login
//const context = require.context('./', true, /app.modules.login.pages.login-page.login-page.component\.spec\.ts$/);

// change password
//const context = require.context('./', true, /app.modules.main.components.account.password-dialog.password-dialog.component\.spec\.ts$/);

// edit profile
//const context = require.context('./', true, /app.modules.main.components.account.profile-dialog.profile-dialog.component\.spec\.ts$/);


// event bus service
// const context = require.context('./', true, /app.services.event-bus.event-bus.service\.spec\.ts$/);


// message service
// const context = require.context('./', true, /app.services.message.message.service\.spec\.ts$/);

// offer service
// const context = require.context('./', true, /app.services.offer.offer.service\.spec\.ts$/);

// post service
// const context = require.context('./', true, /app.services.post.post.service\.spec\.ts$/);

// review service
// const context = require.context('./', true, /app.services.review.review.service\.spec\.ts$/);


// And load the modules.
context.keys().map(context);
