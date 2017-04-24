package com.awiserk.kundalias.demo2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.awiserk.kundalias.demo2.Data.DataProvider;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickResult;

public class EditoryActivity extends AppCompatActivity implements IPickResult {

    private static final int CAMERA_REQUEST = 1888;
    private static final int RC_PHOTO_PICKER = 2;
    Uri mUri;
    /**
     * Card Image View to hold each Item
     */
    private CardView mItemCardView;
    /**
     * ImageView field to add the item Image
     */
    private ImageView mItemImageView;
    /**
     * TextView Label to add Image
     */
    private TextView mItemlabelTextView;
    /**
     * EditText field to enter the item Id
     */
    private Spinner mItemCategorySpinner;
    /**
     * EditText field to enter the item Id
     */
    private EditText mItemIdEditText;
    /**
     * EditText field to enter the item Price
     */
    private EditText mItemCostEditText;
    /**
     * EditText field to enter the item Size1
     */
    private CheckBox mItemSize1Checkbox;
    /**
     * EditText field to enter the item Size2
     */
    private CheckBox mItemSize2Checkbox;
    /**
     * EditText field to enter the item Size3
     */
    private CheckBox mItemSize3Checkbox;
    /**
     * EditText field to enter the item Size4
     */
    private CheckBox mItemSize4Checkbox;
    /**
     * Boolean flag that keeps track of whether the item has been edited (true) or not (false)
     */
    private boolean mItemHasChanged = false;
    /**
     * Content URI for the existing item (null if it's a new item)
     */
    private Uri mCurrentItemUri;

    private int mCategory = DataProvider.CATEGORY_UNKNOWN;

    /**
     * OnTouchListener that listens for any user touches on a View, implying that they are modifying
     * the view, and we change the mItemHasChanged boolean to true.
     */
    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mItemHasChanged = true;
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        // Find all relevant views that we will need to read user input from
        mItemCardView = (CardView) findViewById(R.id.card_image_view);
        mItemImageView = (ImageView) findViewById(R.id.select_imagebutton);
        mItemlabelTextView = (TextView) findViewById(R.id.add_image_text);
        mItemCategorySpinner = (Spinner) findViewById(R.id.spinner_category);
        mItemIdEditText = (EditText) findViewById(R.id.input_id);
        mItemCostEditText = (EditText) findViewById(R.id.input_price);
        mItemSize1Checkbox = (CheckBox) findViewById(R.id.checkbox_size1);
        mItemSize2Checkbox = (CheckBox) findViewById(R.id.checkbox_size2);
        mItemSize3Checkbox = (CheckBox) findViewById(R.id.checkbox_size3);
        mItemSize4Checkbox = (CheckBox) findViewById(R.id.checkbox_size4);

        // Setup OnTouchListeners on all the input fields, so we can determine if the user
        // has touched or modified them. This will let us know if there are unsaved changes
        // or not, if the user tries to leave the editor without saving.
        mItemImageView.setOnTouchListener(mTouchListener);
        mItemCategorySpinner.setOnTouchListener(mTouchListener);
        mItemIdEditText.setOnTouchListener(mTouchListener);
        mItemCostEditText.setOnTouchListener(mTouchListener);
        mItemSize1Checkbox.setOnTouchListener(mTouchListener);
        mItemSize2Checkbox.setOnTouchListener(mTouchListener);
        mItemSize3Checkbox.setOnTouchListener(mTouchListener);
        mItemSize4Checkbox.setOnTouchListener(mTouchListener);


        //Loads the already set image on device rotation
        if (savedInstanceState != null) {
            mUri = savedInstanceState.getParcelable("uri");
            mItemHasChanged = savedInstanceState.getBoolean("itemChanged");
            updateImage(mUri);
        }

        // ImagePickerButton shows an image picker to upload a image for a message
        mItemImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickImageDialog.build(new PickSetup()).show(EditoryActivity.this);
            }
        });


        setupSpinner();

    }

    @Override
    public void onPickResult(PickResult r) {
        if (r.getError() == null) {
            //If you want the Uri.
            //Mandatory to refresh image from Uri.
            //getImageView().setImageURI(null);
            mUri = r.getUri();
            //Setting the real returned image.
            updateImage(mUri);

            //If you want the Bitmap.
            //mItemImageView.setImageBitmap(r.getBitmap());

            //Image path
            //r.getPath();
        } else {
            //Handle possible errors
            //TODO: do what you have to do with r.getError();
            Toast.makeText(this, r.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // Saves the state of image selected on device rotation
    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mUri != null && mItemHasChanged != false) {
            outState.putParcelable("uri", mUri);
            outState.putBoolean("itemChanged", mItemHasChanged);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        mUri = savedInstanceState.getParcelable("uri");
        mItemHasChanged = savedInstanceState.getBoolean("itemChanged");
        updateImage(mUri);
    }

    /**
     * SetImage into the Image view by cleaning up the existing data
     */
    private void updateImage(Uri imageUri) {
        mItemCardView.setAlpha(1.0f);
        mItemlabelTextView.setVisibility(View.GONE);
        mItemImageView.setPadding(0, 0, 0, 0);
        mItemImageView.setImageURI(imageUri);
    }

    /**
     * Setup the dropdown spinner that allows the user to select the Category of the Item.
     */
    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter categorySpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mItemCategorySpinner.setAdapter(categorySpinnerAdapter);

        // Set the integer mSelected to the constant values
        mItemCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.category1))) {
                        mCategory = DataProvider.CATEGORY_1;
                    } else if (selection.equals(getString(R.string.category2))) {
                        mCategory = DataProvider.CATEGORY_2;
                    } else if (selection.equals(getString(R.string.category3))) {
                        mCategory = DataProvider.CATEGORY_3;
                    } else if (selection.equals(getString(R.string.category2))) {
                        mCategory = DataProvider.CATEGORY_4;
                    } else {
                        mCategory = DataProvider.CATEGORY_UNKNOWN;
                    }
                }
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCategory = DataProvider.CATEGORY_UNKNOWN;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu can be updated (some menu items can be hidden or made visible).
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new item, hide the "Delete" menu item.
        if (mCurrentItemUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                // Save Item to database
                //TODO saveItem();
                // Exit activity
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Pop up confirmation dialog for deletion
                showDeleteConfirmationDialog();
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // If the item hasn't changed, continue with navigating up to parent activity
                // which is the {@link CatalogActivity}.
                if (!mItemHasChanged) {
                    NavUtils.navigateUpFromSameTask(EditoryActivity.this);
                    return true;
                }

                // Otherwise if there are unsaved changes, setup a dialog to warn the user.
                // Create a click listener to handle the user confirming that
                // changes should be discarded.
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(EditoryActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * This method is called when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        // If the item hasn't changed, continue with handling back button press
        if (!mItemHasChanged) {
            super.onBackPressed();
            return;
        }

        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        // Create a click listener to handle the user confirming that changes should be discarded.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "Discard" button, close the current activity.
                        finish();
                    }
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }

    /**
     * Prompt the user to confirm that they want to delete this Item.
     */
    private void showDeleteConfirmationDialog() {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the item.
                //TODO deleteItem();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the Item.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    /**
     * Show a dialog that warns the user there are unsaved changes that will be lost
     * if they continue leaving the editor.
     *
     * @param discardButtonClickListener is the click listener for what to do when
     *                                   the user confirms they want to discard their changes
     */
    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the postivie and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the item.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}
