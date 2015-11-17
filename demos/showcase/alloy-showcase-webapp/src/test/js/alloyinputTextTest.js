// http://docs.casperjs.org/en/latest/testing.html#browser-tests
// http://docs.casperjs.org/en/latest/modules/

var url = "http://localhost:8080/alloy-showcase-webapp-2.0.0-SNAPSHOT/views/component.faces?componentPrefix=alloy&componentName=inputtext&componentUseCase=";
var useCase = 'general';
var x = require('casper').selectXPath;

casper.test.begin('Test alloy:inputText ' + useCase, function suite(test) {

	var buttonXpath = "//button[text()='Submit']";
	var buttonSelector = x(buttonXpath); 
	var successXpath = "//div[@class='alloy-field control-group success']";
	var successSelector = x(successXpath);

	casper.start(url + useCase, function () {

		this.test.assertExists(buttonSelector);

		this.click(buttonSelector);
                this.waitUntilVisible(successSelector, function () {
                        this.test.assertExists(successSelector);
                });
	});
	
	casper.then(function () {

		var checkboxXpath = "//input[@class='alloy-select-boolean-checkbox checkbox']";
		var checkboxSelector = x(checkboxXpath); 
                this.test.assertExists(checkboxSelector);
		this.click(checkboxSelector);
		this.waitWhileVisible(successSelector, function () {
			this.click(buttonSelector);
		});
        });

        casper.then(function () {

		var errorTextXpath = "//span[@class='alloy-message help-inline']";
		var errorTextSelector = x(errorTextXpath);
		this.waitUntilVisible(errorTextSelector, function () {
			this.test.assertExists(errorTextSelector);
		});
        });

        casper.then(function () {
		
		var inputXpath = "//input[contains(@id,':text')]";
		var inputSelector = x(inputXpath);
		this.test.assertExists(inputSelector);
		
		var magic = "Hello World!";
		this.sendKeys(inputSelector, magic);
		this.click(buttonSelector);

                var modelValueXpath = "//span[contains(@id,':modelValue')]";
                var modelValueSelector = x(modelValueXpath);
                this.test.assertExists(modelValueSelector);
		
		this.waitForSelectorTextChange(modelValueSelector, function () {
			var modelValueText = this.fetchText(modelValueSelector);
			this.test.assertEquals(modelValueText, magic, "modelValueText equals \"" + magic + "\"");
		});
	});
		
	casper.run(function () {
		test.done();
	});
});

useCase = 'conversion';

casper.test.begin('Test alloy:inputText ' + useCase, function suite(test) {

        var buttonXpath = "//button[text()='Submit']";
        var buttonSelector = x(buttonXpath);
        var inputXpath = "//input[contains(@id,':text')]";
        var inputSelector = x(inputXpath);

        casper.start(url + useCase, function () {

                this.test.assertExists(buttonSelector);
                this.test.assertExists(inputSelector);
                var magic = "april 03 0033";
                this.sendKeys(inputSelector, magic, {reset: true});
                this.click(buttonSelector);

                var errorTextXpath = "//span[@class='alloy-message help-inline']";
                var errorTextSelector = x(errorTextXpath);
                this.waitUntilVisible(errorTextSelector, function () {
                        this.test.assertExists(errorTextSelector);
                });
        });

        casper.then(function () {

                var magicIn = "april 03, 0033";
                var magicOut = "Apr 3, 0033";
                this.sendKeys(inputSelector, magicIn, {reset: true});
                this.click(buttonSelector);

                var modelValueXpath = "(//span[contains(@id,':modelValue')])[1]";
                var modelValueSelector = x(modelValueXpath);
                this.test.assertExists(modelValueSelector);

                this.waitForSelectorTextChange(modelValueSelector, function () {
                        var modelValueText = this.fetchText(modelValueSelector);
                        this.test.assertEquals(modelValueText, magicOut, "modelValueText equals \"" + magicOut + "\"");
                });
        });

	var buttonXpath2 = "(//button[text()='Submit'])[2]";
	var buttonSelector2 = x(buttonXpath2);
        var inputXpath2 = "(//input[contains(@id,':text')])[2]";
        var inputSelector2 = x(inputXpath2);

        casper.then(function () {

                this.test.assertExists(buttonSelector2);
                this.test.assertExists(inputSelector2);
                var magic = "04/03033";
                this.sendKeys(inputSelector2, magic, {reset: true});
                this.click(buttonSelector2);

                var errorTextXpath = "//span[@class='alloy-message help-inline']";
                var errorTextSelector = x(errorTextXpath);
                this.waitUntilVisible(errorTextSelector, function () {
                        this.test.assertExists(errorTextSelector);
                });
        });

        casper.then(function () {

                var magicIn = "4/3/33";
                var magicOut = "04/03/0033";
                this.sendKeys(inputSelector2, magicIn, {reset: true});
                this.click(buttonSelector2);

                var modelValueXpath2 = "(//span[contains(@id,':modelValue')])[2]";
                var modelValueSelector = x(modelValueXpath2);
                this.test.assertExists(modelValueSelector);

                this.waitForSelectorTextChange(modelValueSelector, function () {
                        var modelValueText = this.fetchText(modelValueSelector);
                        this.test.assertEquals(modelValueText, magicOut, "modelValueTextRight equals \"" + magicOut + "\"");
                });
        });
        casper.run(function () {
                test.done();
        });
});
